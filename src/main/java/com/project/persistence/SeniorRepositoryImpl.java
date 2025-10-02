package com.project.persistence;

import static com.project.domain.senior.QDoll.doll;
import static com.project.domain.senior.QSenior.senior;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.project.domain.analysis.Risk;
import com.project.domain.senior.Gu;
import com.project.domain.senior.Beopjeongdong;
import com.project.domain.senior.Sex;
import com.project.dto.request.SeniorSearchCondition;
import com.project.dto.response.QSeniorListResponseDto;
import com.project.dto.response.SeniorListResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SeniorRepositoryImpl implements SeniorRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SeniorListResponseDto> searchSeniors(SeniorSearchCondition condition, Pageable pageable) {
        List<SeniorListResponseDto> content = queryFactory
                .select(new QSeniorListResponseDto(
                        senior.id,
                        senior.name,
                        senior.birthDate,
                        senior.sex,
                        senior.address.gu,
                        senior.address.dong,
                        senior.state,
                        doll.id,
                        senior.phone,
                        senior.createdAt
                ))
                .from(senior)
                .leftJoin(senior.doll, doll)
                .where(
                        seniorIdEq(condition.getSeniorId()),
                        nameContains(condition.getName()),
                        phoneContains(condition.getPhone()),
                        sexEq(condition.getSex()),
                        guEq(condition.getGu()),
                        dongEq(condition.getDong()),
                        stateEq(condition.getState()),
                        dollIdEq(condition.getDollId()),
                        ageGroupEq(condition.getAgeGroup())
                )
                .orderBy(senior.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        
        Long total = queryFactory
                .select(senior.count())
                .from(senior)
                .leftJoin(senior.doll, doll)
                .where(
                        seniorIdEq(condition.getSeniorId()),
                        nameContains(condition.getName()),
                        phoneContains(condition.getPhone()),
                        sexEq(condition.getSex()),
                        guEq(condition.getGu()),
                        dongEq(condition.getDong()),
                        stateEq(condition.getState()),
                        dollIdEq(condition.getDollId()),
                        ageGroupEq(condition.getAgeGroup())
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }

    private BooleanExpression seniorIdEq(Long seniorId) {
        return seniorId != null ? senior.id.eq(seniorId) : null;
    }

    private BooleanExpression nameContains(String name) {
        return StringUtils.hasText(name) ? senior.name.contains(name) : null;
    }
    
    private BooleanExpression phoneContains(String phone) {
        return StringUtils.hasText(phone) ? senior.phone.contains(phone) : null;
    }

    private BooleanExpression sexEq(Sex sex) {
        return sex != null ? senior.sex.eq(sex) : null;
    }

    private BooleanExpression guEq(Gu gu) {
        return gu != null ? senior.address.gu.eq(gu) : null;
    }

    private BooleanExpression dongEq(Beopjeongdong dong) {
        return dong != null ? senior.address.dong.eq(dong) : null;
    }
    
    private BooleanExpression stateEq(Risk state) {
        return state != null ? senior.state.eq(state) : null;
    }

    private BooleanExpression dollIdEq(String dollId) {
        return StringUtils.hasText(dollId) ? doll.id.eq(dollId) : null;
    }
    
    private BooleanExpression ageGroupEq(Integer ageGroup) {
        if (ageGroup == null) {
            return null;
        }
        LocalDate now = LocalDate.now();
        if (ageGroup == 100) {
            LocalDate birthDateLimit = now.minusYears(100);
            return senior.birthDate.loe(birthDateLimit);
        } else {
            LocalDate startDate = now.minusYears(ageGroup + 9).minusDays(1);
            LocalDate endDate = now.minusYears(ageGroup);
            return senior.birthDate.between(startDate, endDate);
        }
    }
}