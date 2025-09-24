package com.project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.domain.analysis.ConfidenceScores;
import com.project.domain.analysis.Dialogue;
import com.project.domain.analysis.OverallResult;
import com.project.domain.analysis.Reason;
import com.project.domain.analysis.Risk;
import com.project.domain.member.Member;
import com.project.domain.member.Role;
import com.project.domain.senior.Doll;
import com.project.domain.senior.Guardian;
import com.project.domain.senior.MedicalInfo;
import com.project.domain.senior.Senior;
import com.project.domain.senior.Sex;
import com.project.persistence.MemberRepository;
import com.project.persistence.SeniorRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInit implements ApplicationRunner {
    private final PasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final SeniorRepository seniorRepository;
    
    private final Random random = new Random();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (memberRepository.count() == 0) {
            System.out.println(" ========= 멤버 생성 =========");
            Member admin = Member.builder().username("admin").password(encoder.encode("admin")).role(Role.ROLE_ADMIN).enabled(true).build();
            Member member = Member.builder().username("member").password(encoder.encode("member")).role(Role.ROLE_MEMBER).enabled(true).build();
            memberRepository.save(admin);
            memberRepository.save(member);
            System.out.println(" ========= 멤버 생성 완료 =========");
        }

        if (seniorRepository.count() == 0) {
            System.out.println(" ========= Senior 더미 데이터 생성 =========");

            String[] firstNames = {"김", "이", "박", "최", "정", "강", "조", "윤", "장", "임"};
            String[] lastNames = {"영수", "철수", "영희", "순자", "민준", "서연", "지우", "하은", "도윤", "시우"};
            String[] diseases = {"고혈압", "당뇨병", "관절염", "심장질환", "치매", "골다공증"};
            String[] medications = {"혈압약", "인슐린", "진통제", "아스피린", "영양제"};
            String[] relationships = {"자녀", "배우자", "손주", "며느리"};

            String[] positiveDialogues = {"오늘 날씨가 참 좋구나.", "밥은 먹었니?", "산책이라도 다녀와야겠다.", "예쁜 꽃을 보니 기분이 좋네."};
            String[] dangerDialogues = {"무릎이 좀 쑤시네.", "요즘 입맛이 통 없어.", "밤에 잠을 좀 설쳤어.", "기운이 하나도 없네."};
            String[] criticalDialogues = {"가슴이 답답하고 숨이 좀 차.", "어지러워서 일어날 수가 없어.", "갑자기 머리가 너무 아파.", "속이 계속 메스꺼워."};
            String[] emergencyDialogues = {"숨을 쉴 수가 없어!", "가슴이 찢어질 것 같아!", "도와주세요!", "사람 살려!"};

            List<Senior> seniorList = new ArrayList<>();
            for (int i = 1; i <= 15; i++) {
                String seniorName = firstNames[random.nextInt(firstNames.length)] + lastNames[random.nextInt(lastNames.length)];
                Sex sex = random.nextBoolean() ? Sex.MALE : Sex.FEMALE;

                Guardian guardian = Guardian.builder()
                        .guardianName(firstNames[random.nextInt(firstNames.length)] + lastNames[random.nextInt(lastNames.length)])
                        .guardianPhone("010-1234-56" + String.format("%02d", i))
                        .relationship(relationships[random.nextInt(relationships.length)])
                        .guardianNote("특별한 사항 없음")
                        .build();

                MedicalInfo medicalInfo = MedicalInfo.builder()
                        .diseases(diseases[random.nextInt(diseases.length)])
                        .medications(medications[random.nextInt(medications.length)])
                        .build();

                Senior senior = Senior.builder()
                        .name(seniorName)
                        .birthDate(LocalDate.of(1930 + random.nextInt(20), 1, 1).plusDays(random.nextInt(365)))
                        .sex(sex)
                        .phone("010-9876-54" + String.format("%02d", i))
                        .address("서울특별시 어딘가 " + i + "번지")
                        .note("초기 등록")
                        .guardian(guardian)
                        .medicalInfo(medicalInfo)
                        .build();

                Doll doll = Doll.builder()
                        .id(UUID.randomUUID().toString())
                        .build();
                senior.changeDoll(doll);

                int overallResultCount = random.nextInt(3) + 2;
                for (int j = 0; j < overallResultCount; j++) {
                    Risk overallRisk = Risk.values()[random.nextInt(Risk.values().length)];
                    ConfidenceScores overallScores = createConfidenceScores(overallRisk);

                    Reason reason = Reason.builder()
                            .reasons(Arrays.asList(
                                    overallRisk.name() + " 위험으로 판단되는 대화가 발견되었습니다.",
                                    "주요 키워드: '" + (overallRisk == Risk.POSITIVE ? "날씨" : "아프다") + "', '" + (overallRisk == Risk.POSITIVE ? "기분" : "어지러움") + "'"
                            ))
                            .summary(seniorName + "님의 최근 대화 분석 결과, " + overallRisk.name() + " 수준의 주의가 필요합니다.")
                            .build();

                    OverallResult overallResult = OverallResult.builder()
                            .doll(doll)
                            .label(overallRisk)
                            .confidenceScores(overallScores)
                            .reason(reason)
                            .build();

                    int dialogueCount = random.nextInt(4) + 3;
                    for (int k = 0; k < dialogueCount; k++) {
                        Risk dialogueRisk = Risk.values()[random.nextInt(Risk.values().length)];
                        String text = "";
                        switch (dialogueRisk) {
                            case POSITIVE: text = positiveDialogues[random.nextInt(positiveDialogues.length)]; break;
                            case DANGER: text = dangerDialogues[random.nextInt(dangerDialogues.length)]; break;
                            case CRITICAL: text = criticalDialogues[random.nextInt(criticalDialogues.length)]; break;
                            case EMERGENCY: text = emergencyDialogues[random.nextInt(emergencyDialogues.length)]; break;
                        }

                        Dialogue dialogue = Dialogue.builder()
                                .text(text)
                                .utteredAt(LocalDateTime.now().minusDays(j).minusHours(k * 2L))
                                .label(dialogueRisk)
                                .confidenceScores(createConfidenceScores(dialogueRisk))
                                .build();
                        overallResult.addDialogue(dialogue); 
                    }
                    doll.getOverallResults().add(overallResult);
                }
                seniorList.add(senior);
            }
            seniorRepository.saveAll(seniorList);
            System.out.println(" ========= Senior 더미 데이터 생성 완료 =========");
        }
    }

    private ConfidenceScores createConfidenceScores(Risk mainRisk) {
        double positive = 0.1, danger = 0.1, critical = 0.1, emergency = 0.1;
        
        switch (mainRisk) {
            case POSITIVE: positive = 0.6 + random.nextDouble() * 0.3; break;
            case DANGER: danger = 0.6 + random.nextDouble() * 0.3; break;
            case CRITICAL: critical = 0.6 + random.nextDouble() * 0.3; break;
            case EMERGENCY: emergency = 0.6 + random.nextDouble() * 0.3; break;
        }

        double sum = positive + danger + critical + emergency;
        return ConfidenceScores.builder()
                .positive(positive / sum)
                .danger(danger / sum)
                .critical(critical / sum)
                .emergency(emergency / sum)
                .build();
    }
}