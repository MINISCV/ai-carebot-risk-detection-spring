package com.project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
import com.project.domain.senior.Address;
import com.project.domain.senior.Doll;
import com.project.domain.senior.Gu;
import com.project.domain.senior.Guardian;
import com.project.domain.senior.Haengjeongdong;
import com.project.domain.senior.MedicalInfo;
import com.project.domain.senior.Residence;
import com.project.domain.senior.Senior;
import com.project.domain.senior.Sex;
import com.project.persistence.MemberRepository;
import com.project.persistence.SeniorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            log.info(" ========= 멤버 생성 =========");
            Member admin = Member.builder().username("admin").password(encoder.encode("admin")).role(Role.ROLE_ADMIN).enabled(true).build();
            Member member = Member.builder().username("member").password(encoder.encode("member")).role(Role.ROLE_MEMBER).enabled(true).build();
            memberRepository.save(admin);
            memberRepository.save(member);
            log.info(" ========= 멤버 생성 완료 =========");
        }

        if (seniorRepository.count() == 0) {
            log.info(" ========= Senior 더미 데이터 생성 (2000명) 시작 =========");
            final int TOTAL_COUNT = 2000;
            final int BATCH_SIZE = 100;

            String[] firstNames = {"김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "한", "오", "서", "신", "권", "황", "안", "송", "류", "전", "홍", "고", "문", "양", "손", "배"};
            String[] lastNames = {"영수", "철수", "영희", "순자", "민준", "서연", "지우", "하은", "도윤", "시우", "정숙", "광호", "미영", "상철", "은주", "현우", "다은", "성민", "지혜", "동현", "민지", "영철", "순희"};
            String[] diseases = {"고혈압", "당뇨병", "관절염", "심장질환", "치매", "골다공증", "우울증", "난청", "백내장", "파킨슨병", "만성 폐쇄성 폐질환", null, null, null}; // null 비율 증가
            String[] medications = {"혈압약", "인슐린", "소염진통제", "아스피린", "영양제", "항우울제", "수면제", "치매 약", "혈액순환개선제", null, null, null}; // null 비율 증가
            String[] relationships = {"자녀(아들)", "자녀(딸)", "배우자", "손주", "며느리", "사위", "조카", "형제/자매"};
            
            String[] positiveDialogues = {"오늘 날씨가 참 좋구나.", "밥은 먹었니?", "산책이라도 다녀와야겠다.", "예쁜 꽃을 보니 기분이 좋네.", "손주가 보고 싶구나.", "옛날 생각나네.", "텔레비전이 재밌는 걸 많이 하네.", "노래나 한 곡 들어볼까?", "오늘따라 몸이 개운하네.", "친구랑 통화하니 즐겁구나."};
            String[] dangerDialogues = {"무릎이 좀 쑤시네.", "요즘 입맛이 통 없어.", "밤에 잠을 좀 설쳤어.", "기운이 하나도 없네.", "자꾸 깜빡깜빡하네.", "밖에 나가기가 귀찮아.", "적적하고 심심하구나.", "전화 오는 곳이 없네.", "어깨가 결리는구만."};
            String[] criticalDialogues = {"가슴이 답답하고 숨이 좀 차.", "어지러워서 일어날 수가 없어.", "갑자기 머리가 너무 아파.", "속이 계속 메스꺼워.", "다리에 힘이 쭉 빠지네.", "가슴이 두근거려서 잠을 잘 수가 없어.", "열이 나는 것 같아.", "식은땀이 계속 흐르네."};
            String[] emergencyDialogues = {"숨을 쉴 수가 없어!", "가슴이 찢어질 것 같아!", "도와주세요!", "사람 살려!", "넘어졌어... 일어날 수가 없어.", "말이 잘 안 나와...", "갑자기 몸 한쪽에 마비가 오는 것 같아!"};
            String[] summaryTemplates = {
                "%s님의 최근 대화에서 외로움과 관련된 표현이 감지되었습니다.",
                "%s님께서 가벼운 신체적 불편함을 자주 언급하셨습니다. 지속적인 관찰이 필요합니다.",
                "%s님의 대화 패턴 분석 결과, 긍정적인 감정 상태를 유지하고 계십니다.",
                "긴급 상황으로 의심되는 %s님의 발언이 포착되었습니다. 즉각적인 확인이 필요합니다.",
                "심각한 통증을 호소하는 %s님의 대화가 발견되었습니다. 건강 상태 확인이 시급합니다."
            };

            List<Senior> seniorBatchList = new ArrayList<>();
            for (int i = 1; i <= TOTAL_COUNT; i++) {
                String seniorName = firstNames[random.nextInt(firstNames.length)] + lastNames[random.nextInt(lastNames.length)];
                Sex sex = random.nextBoolean() ? Sex.MALE : Sex.FEMALE;
                Gu randomGu = Gu.values()[random.nextInt(Gu.values().length)];
                List<Haengjeongdong> dongsInGu = Haengjeongdong.findAllByGu(randomGu);
                Haengjeongdong randomDong = dongsInGu.get(random.nextInt(dongsInGu.size()));
                Address address = Address.builder().address("대전광역시 " + randomGu.getKoreanName() + " " + randomDong.getKoreanName() + " " + (random.nextInt(200) + 1) + "번지").gu(randomGu).dong(randomDong).build();
                String disease = diseases[random.nextInt(diseases.length)];
                String medication = disease == null ? null : medications[random.nextInt(medications.length)];
                MedicalInfo medicalInfo = MedicalInfo.builder().diseases(disease).medications(medication).note(disease == null ? "특이사항 없음" : "정기적인 관리 필요").build();
                Guardian guardian = Guardian.builder().guardianName(firstNames[random.nextInt(firstNames.length)] + "보호자").guardianPhone("010-1234-" + String.format("%04d", i)).relationship(relationships[random.nextInt(relationships.length)]).guardianNote("오후에 연락 선호").build();
                Senior senior = Senior.builder().name(seniorName).birthDate(LocalDate.of(1925 + random.nextInt(30), 1, 1).plusDays(random.nextInt(365))).sex(sex).residence(Residence.values()[random.nextInt(Residence.values().length)]).phone("010-9876-" + String.format("%04d", i)).address(address).note("초기 등록").guardian(guardian).medicalInfo(medicalInfo).build();
                Doll doll = Doll.builder().id("doll-" + String.format("%04d", i)).build();
                senior.changeDoll(doll);
                int overallResultCount = random.nextInt(5) + 3;
                Risk latestRisk = Risk.POSITIVE;
                for (int j = 0; j < overallResultCount; j++) {
                    int riskScore = random.nextInt(100);
                    Risk overallRisk;
                    if (riskScore < 60) overallRisk = Risk.POSITIVE; else if (riskScore < 85) overallRisk = Risk.DANGER; else if (riskScore < 95) overallRisk = Risk.CRITICAL; else overallRisk = Risk.EMERGENCY;
                    if (j == 0) latestRisk = overallRisk;
                    ConfidenceScores overallScores = createConfidenceScores(overallRisk);
                    Reason reason = Reason.builder().reasons(Collections.singletonList(overallRisk.name() + " 위험으로 판단되는 대화가 발견되었습니다.")).summary(String.format(summaryTemplates[random.nextInt(summaryTemplates.length)], seniorName)).build();
                    OverallResult overallResult = OverallResult.builder().senior(senior).doll(senior.getDoll()).label(overallRisk).confidenceScores(overallScores).reason(reason).build();
                    int dialogueCount = random.nextInt(8) + 5;
                    for (int k = 0; k < dialogueCount; k++) {
                        Risk dialogueRisk = getBiasedDialogueRisk(overallRisk, random);
                        String text = "";
                        switch (dialogueRisk) {
                            case POSITIVE: text = positiveDialogues[random.nextInt(positiveDialogues.length)]; break;
                            case DANGER: text = dangerDialogues[random.nextInt(dangerDialogues.length)]; break;
                            case CRITICAL: text = criticalDialogues[random.nextInt(criticalDialogues.length)]; break;
                            case EMERGENCY: text = emergencyDialogues[random.nextInt(emergencyDialogues.length)]; break;
                        }
                        Dialogue dialogue = Dialogue.builder().text(text).utteredAt(LocalDateTime.now().minusDays(j * 7L).minusHours(k * 2L)).label(dialogueRisk).confidenceScores(createConfidenceScores(dialogueRisk)).build();
                        overallResult.addDialogue(dialogue);
                    }
                    senior.getOverallResults().add(overallResult);
                }
                senior.updateState(latestRisk);

                // --- 배치 처리 로직 ---
                seniorBatchList.add(senior);
                if (i % BATCH_SIZE == 0 || i == TOTAL_COUNT) {
                    seniorRepository.saveAll(seniorBatchList);
                    seniorBatchList.clear();
                    log.info(" > {} / {} 명의 시니어 데이터 생성 완료", i, TOTAL_COUNT);
                }
            }
            log.info(" ========= Senior 더미 데이터 생성 (2000명) 완료 =========");
        }
    }

    private ConfidenceScores createConfidenceScores(Risk mainRisk) {
        double positive = 0.1, danger = 0.1, critical = 0.1, emergency = 0.1;
        switch (mainRisk) {
            case POSITIVE: positive = 0.7 + random.nextDouble() * 0.2; break;
            case DANGER: danger = 0.7 + random.nextDouble() * 0.2; break;
            case CRITICAL: critical = 0.7 + random.nextDouble() * 0.2; break;
            case EMERGENCY: emergency = 0.7 + random.nextDouble() * 0.2; break;
        }
        double sum = positive + danger + critical + emergency;
        return ConfidenceScores.builder().positive(positive / sum).danger(danger / sum).critical(critical / sum).emergency(emergency / sum).build();
    }

    private Risk getBiasedDialogueRisk(Risk overallRisk, Random random) {
        int chance = random.nextInt(100);
        switch (overallRisk) {
            case POSITIVE: return chance < 90 ? Risk.POSITIVE : Risk.DANGER;
            case DANGER: if (chance < 60) return Risk.DANGER; if (chance < 95) return Risk.POSITIVE; return Risk.CRITICAL;
            case CRITICAL: if (chance < 65) return Risk.CRITICAL; if (chance < 90) return Risk.DANGER; return Risk.EMERGENCY;
            case EMERGENCY: return chance < 70 ? Risk.EMERGENCY : Risk.CRITICAL;
            default: return Risk.POSITIVE;
        }
    }
}