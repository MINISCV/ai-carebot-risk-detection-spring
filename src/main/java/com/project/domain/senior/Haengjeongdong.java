package com.project.domain.senior;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Haengjeongdong {
	// 동구
    JUNGANG_DONG("중앙동", Gu.DONG_GU),
    SININ_DONG("신인동", Gu.DONG_GU),
    HYO_DONG("효 동", Gu.DONG_GU),
    PANAM_1_DONG("판암1동", Gu.DONG_GU),
    PANAM_2_DONG("판암2동", Gu.DONG_GU),
    YONGUN_DONG("용운동", Gu.DONG_GU),
    DAE_DONG("대동", Gu.DONG_GU),
    JAYANG_DONG("자양동", Gu.DONG_GU),
    GAYANG_1_DONG("가양1동", Gu.DONG_GU),
    GAYANG_2_DONG("가양2동", Gu.DONG_GU),
    YONGJEON_DONG("용전동", Gu.DONG_GU),
    SEONGNAM_DONG("성남동", Gu.DONG_GU),
    HONGDO_DONG("홍도동", Gu.DONG_GU),
    SAMSUNG_DONG("삼성동", Gu.DONG_GU),
    DAECHEONG_DONG("대청동", Gu.DONG_GU),
    SANNAE_DONG("산내동", Gu.DONG_GU),

    // 중구
    EUNHAENGSEONHWA_DONG("은행선화동", Gu.JUNG_GU),
    MOK_DONG("목동", Gu.JUNG_GU),
    JUNGCHON_DONG("중촌동", Gu.JUNG_GU),
    DAEHEUNG_DONG("대흥동", Gu.JUNG_GU),
    MUNCHANG_DONG("문창동", Gu.JUNG_GU),
    SEOKGYO_DONG("석교동", Gu.JUNG_GU),
    DAESA_DONG("대사동", Gu.JUNG_GU),
    BUSA_DONG("부사동", Gu.JUNG_GU),
    YONGDU_DONG("용두동", Gu.JUNG_GU),
    ORYU_DONG("오류동", Gu.JUNG_GU),
    TAEPYEONG_1_DONG("태평1동", Gu.JUNG_GU),
    TAEPYEONG_2_DONG("태평2동", Gu.JUNG_GU),
    YUCHEON_1_DONG("유천1동", Gu.JUNG_GU),
    YUCHEON_2_DONG("유천2동", Gu.JUNG_GU),
    MUNHWA_1_DONG("문화1동", Gu.JUNG_GU),
    MUNHWA_2_DONG("문화2동", Gu.JUNG_GU),
    SANSEONG_DONG("산성동", Gu.JUNG_GU),

    // 서구
    BOKSU_DONG("복수동", Gu.SEO_GU),
    DOMA_1_DONG("도마1동", Gu.SEO_GU),
    DOMA_2_DONG("도마2동", Gu.SEO_GU),
    JEONGLIM_DONG("정림동", Gu.SEO_GU),
    BYEON_DONG("변동", Gu.SEO_GU),
    YONGMUN_DONG("용문동", Gu.SEO_GU),
    TANBANG_DONG("탄방동", Gu.SEO_GU),
    DUNSAN_1_DONG("둔산1동", Gu.SEO_GU),
    DUNSAN_2_DONG("둔산2동", Gu.SEO_GU),
    DUNSAN_3_DONG("둔산3동", Gu.SEO_GU),
    GOEJEONG_DONG("괴정동", Gu.SEO_GU),
    GAJANG_DONG("가장동", Gu.SEO_GU),
    NAE_DONG("내동", Gu.SEO_GU),
    GALMA_1_DONG("갈마1동", Gu.SEO_GU),
    GALMA_2_DONG("갈마2동", Gu.SEO_GU),
    WOLPYEONG_1_DONG("월평1동", Gu.SEO_GU),
    WOLPYEONG_2_DONG("월평2동", Gu.SEO_GU),
    WOLPYEONG_3_DONG("월평3동", Gu.SEO_GU),
    MANNYEON_DONG("만년동", Gu.SEO_GU),
    GASUWON_DONG("가수원동", Gu.SEO_GU),
    DOAN_DONG("도안동", Gu.SEO_GU),
    GWANJEO_1_DONG("관저1동", Gu.SEO_GU),
    GWANJEO_2_DONG("관저2동", Gu.SEO_GU),
    GISEONG_DONG("기성동", Gu.SEO_GU),

    // 유성구
    JINJAM_DONG("진잠동", Gu.YUSEONG_GU),
    HAKHA_DONG("학하동", Gu.YUSEONG_GU),
    WONSINHEUNG_DONG("원신흥동", Gu.YUSEONG_GU),
    SANGDAE_DONG("상대동", Gu.YUSEONG_GU),
    ONCHEON_1_DONG("온천1동", Gu.YUSEONG_GU),
    ONCHEON_2_DONG("온천2동", Gu.YUSEONG_GU),
    NOEUN_1_DONG("노은1동", Gu.YUSEONG_GU),
    NOEUN_2_DONG("노은2동", Gu.YUSEONG_GU),
    NOEUN_3_DONG("노은3동", Gu.YUSEONG_GU),
    SINSEONG_DONG("신성동", Gu.YUSEONG_GU),
    JEONMIN_DONG("전민동", Gu.YUSEONG_GU),
    GUJEUK_DONG("구즉동", Gu.YUSEONG_GU),
    GWANPYEONG_DONG("관평동", Gu.YUSEONG_GU),

    // 대덕구
    OJEONG_DONG("오정동", Gu.DAEDEOK_GU),
    DAEHWA_DONG("대화동", Gu.DAEDEOK_GU),
    HOEDEOK_DONG("회덕동", Gu.DAEDEOK_GU),
    BIRAE_DONG("비래동", Gu.DAEDEOK_GU),
    SONGCHON_DONG("송촌동", Gu.DAEDEOK_GU),
    JUNGNI_DONG("중리동", Gu.DAEDEOK_GU),
    BEOB_1_DONG("법1동", Gu.DAEDEOK_GU),
    BEOB_2_DONG("법2동", Gu.DAEDEOK_GU),
    SINTANJIN_DONG("신탄진동", Gu.DAEDEOK_GU),
    SEOKBONG_DONG("석봉동", Gu.DAEDEOK_GU),
    DEOKAM_DONG("덕암동", Gu.DAEDEOK_GU),
    MOKSANG_DONG("목상동", Gu.DAEDEOK_GU);

    private final String koreanName;
    private final Gu gu;

    public static List<Haengjeongdong> findAllByGu(Gu gu) {
        return Arrays.stream(Haengjeongdong.values())
                .filter(dong -> dong.getGu() == gu)
                .collect(Collectors.toList());
    }

    @JsonCreator
    public static Haengjeongdong from(String value) {
        if (value == null) return null;
        String upperValue = value.toUpperCase();
        for (Haengjeongdong dong : Haengjeongdong.values()) {
            if (dong.name().equals(upperValue) || dong.getKoreanName().equals(value)) {
                return dong;
            }
        }
        throw new IllegalArgumentException("알 수 없는 Haengjeongdong 값입니다: " + value);
    }
}