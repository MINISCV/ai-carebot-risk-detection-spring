package com.project.domain.senior;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Beopjeongdong {
    // 동구 (Dong-gu)
    WON_DONG("원동", Gu.DONG_GU),
    JEONG_DONG("정동", Gu.DONG_GU),
    JUNG_DONG("중동", Gu.DONG_GU),
    SOJE_DONG("소제동", Gu.DONG_GU),
    SINAN_DONG("신안동", Gu.DONG_GU),
    IN_DONG("인동", Gu.DONG_GU),
    SINHEUNG_DONG("신흥동", Gu.DONG_GU),
    HYO_DONG("효동", Gu.DONG_GU),
    CHEON_DONG("천동", Gu.DONG_GU),
    GAO_DONG("가오동", Gu.DONG_GU),
    PANAM_DONG("판암동", Gu.DONG_GU),
    SAMJEONG_DONG_DONG_GU("삼정동", Gu.DONG_GU),
    YONGUN_DONG("용운동", Gu.DONG_GU),
    DAE_DONG_DONG_GU("대동", Gu.DONG_GU),
    JAYANG_DONG("자양동", Gu.DONG_GU),
    GAYANG_DONG("가양동", Gu.DONG_GU),
    YONGJEON_DONG("용전동", Gu.DONG_GU),
    SEONGNAM_DONG("성남동", Gu.DONG_GU),
    HONGDO_DONG("홍도동", Gu.DONG_GU),
    SAMSUNG_DONG("삼성동", Gu.DONG_GU),
    CHU_DONG("추동", Gu.DONG_GU),
    BIRYONG_DONG("비룡동", Gu.DONG_GU),
    JUSAN_DONG("주산동", Gu.DONG_GU),
    YONGGYE_DONG_DONG_GU("용계동", Gu.DONG_GU),
    MASAN_DONG("마산동", Gu.DONG_GU),
    HYOPYEONG_DONG("효평동", Gu.DONG_GU),
    JIK_DONG("직동", Gu.DONG_GU),
    SECHEON_DONG("세천동", Gu.DONG_GU),
    SIN상_DONG("신상동", Gu.DONG_GU),
    SINHA_DONG("신하동", Gu.DONG_GU),
    SINCHON_DONG("신촌동", Gu.DONG_GU),
    SASEONG_DONG("사성동", Gu.DONG_GU),
    NAETAP_DONG("내탑동", Gu.DONG_GU),
    O_DONG_DONG_GU("오동", Gu.DONG_GU),
    JUCHON_DONG("주촌동", Gu.DONG_GU),
    NANGWOL_DONG("낭월동", Gu.DONG_GU),
    DAEBYEOL_DONG("대별동", Gu.DONG_GU),
    ISA_DONG("이사동", Gu.DONG_GU),
    DAESEONG_DONG("대성동", Gu.DONG_GU),
    JANGCHEOK_DONG("장척동", Gu.DONG_GU),
    SOHO_DONG("소호동", Gu.DONG_GU),
    GUDO_DONG("구도동", Gu.DONG_GU),
    SAMGOE_DONG("삼괴동", Gu.DONG_GU),
    SANGSO_DONG("상소동", Gu.DONG_GU),
    HASO_DONG("하소동", Gu.DONG_GU),

    // 중구 (Jung-gu)
    EUNHAENG_DONG("은행동", Gu.JUNG_GU),
    SEONHWA_DONG("선화동", Gu.JUNG_GU),
    MOK_DONG("목동", Gu.JUNG_GU),
    JUNGCHON_DONG("중촌동", Gu.JUNG_GU),
    DAEHEUNG_DONG("대흥동", Gu.JUNG_GU),
    MUNCHANG_DONG("문창동", Gu.JUNG_GU),
    SEOKGYO_DONG("석교동", Gu.JUNG_GU),
    OKGYE_DONG("옥계동", Gu.JUNG_GU),
    HO_DONG("호동", Gu.JUNG_GU),
    DAESA_DONG("대사동", Gu.JUNG_GU),
    BUSA_DONG("부사동", Gu.JUNG_GU),
    YONGDU_DONG("용두동", Gu.JUNG_GU),
    ORYU_DONG("오류동", Gu.JUNG_GU),
    TAEPYEONG_DONG("태평동", Gu.JUNG_GU),
    YUCHEON_DONG("유천동", Gu.JUNG_GU),
    MUNHWA_DONG("문화동", Gu.JUNG_GU),
    SANSEONG_DONG("산성동", Gu.JUNG_GU),
    SAJEONG_DONG("사정동", Gu.JUNG_GU),
    ANYEONG_DONG("안영동", Gu.JUNG_GU),
    GUWAN_DONG("구완동", Gu.JUNG_GU),
    MUSU_DONG("무수동", Gu.JUNG_GU),
    CHIMSAN_DONG("침산동", Gu.JUNG_GU),
    MOKDAL_DONG("목달동", Gu.JUNG_GU),
    JEONGSAENG_DONG("정생동", Gu.JUNG_GU),
    EONAM_DONG("어남동", Gu.JUNG_GU),
    GEUM_DONG("금동", Gu.JUNG_GU),

    // 서구 (Seo-gu)
    BOKSU_DONG("복수동", Gu.SEO_GU),
    DOMA_DONG("도마동", Gu.SEO_GU),
    JEONGLIM_DONG("정림동", Gu.SEO_GU),
    GOEGOK_DONG("괴곡동", Gu.SEO_GU),
    BYEON_DONG("변동", Gu.SEO_GU),
    YONGMUN_DONG("용문동", Gu.SEO_GU),
    TANBANG_DONG("탄방동", Gu.SEO_GU),
    DUNSAN_DONG("둔산동", Gu.SEO_GU),
    GOEJEONG_DONG("괴정동", Gu.SEO_GU),
    GAJANG_DONG("가장동", Gu.SEO_GU),
    NAE_DONG("내동", Gu.SEO_GU),
    GALMA_DONG("갈마동", Gu.SEO_GU),
    WOLPYEONG_DONG("월평동", Gu.SEO_GU),
    MANNYEON_DONG("만년동", Gu.SEO_GU),
    GASUWON_DONG("가수원동", Gu.SEO_GU),
    DOAN_DONG("도안동", Gu.SEO_GU),
    GWANJEO_DONG("관저동", Gu.SEO_GU),
    HEUKSEOK_DONG("흑석동", Gu.SEO_GU),
    MAENO_DONG("매노동", Gu.SEO_GU),
    SANJIK_DONG("산직동", Gu.SEO_GU),
    JANGAN_DONG("장안동", Gu.SEO_GU),
    PYEONGCHON_DONG_SEO_GU("평촌동", Gu.SEO_GU),
    O_DONG_SEO_GU("오동", Gu.SEO_GU),
    UMYEONG_DONG("우명동", Gu.SEO_GU),
    WONJEONG_DONG("원정동", Gu.SEO_GU),
    YONGCHON_DONG("용촌동", Gu.SEO_GU),
    BONGGOK_DONG("봉곡동", Gu.SEO_GU),

    // 유성구 (Yuseong-gu)
    SEONGBUK_DONG("성북동", Gu.YUSEONG_GU),
    SE_DONG("세동", Gu.YUSEONG_GU),
    SONGJEONG_DONG("송정동", Gu.YUSEONG_GU),
    BANG_DONG("방동", Gu.YUSEONG_GU),
    WONNAE_DONG("원내동", Gu.YUSEONG_GU),
    GYOCHON_DONG("교촌동", Gu.YUSEONG_GU),
    DAEJEONG_DONG("대정동", Gu.YUSEONG_GU),
    YONGGYE_DONG_YUSEONG_GU("용계동", Gu.YUSEONG_GU),
    HAKHA_DONG("학하동", Gu.YUSEONG_GU),
    GYESAN_DONG("계산동", Gu.YUSEONG_GU),
    DEOKMYEONG_DONG("덕명동", Gu.YUSEONG_GU),
    BOKYONG_DONG("복용동", Gu.YUSEONG_GU),
    WONSINHEUNG_DONG("원신흥동", Gu.YUSEONG_GU),
    BONGMYEONG_DONG("봉명동", Gu.YUSEONG_GU),
    SANGDAE_DONG("상대동", Gu.YUSEONG_GU),
    GUAM_DONG("구암동", Gu.YUSEONG_GU),
    JANGDAE_DONG("장대동", Gu.YUSEONG_GU),
    JUK_DONG("죽동", Gu.YUSEONG_GU),
    GUNG_DONG("궁동", Gu.YUSEONG_GU),
    EOEUN_DONG("어은동", Gu.YUSEONG_GU),
    GUSEONG_DONG("구성동", Gu.YUSEONG_GU),
    GAP_DONG("갑동", Gu.YUSEONG_GU),
    NOEUN_DONG("노은동", Gu.YUSEONG_GU),
    HAGI_DONG("하기동", Gu.YUSEONG_GU),
    JIJOK_DONG("지족동", Gu.YUSEONG_GU),
    SUNAM_DONG("수남동", Gu.YUSEONG_GU),
    ANSAN_DONG("안산동", Gu.YUSEONG_GU),
    OESAM_DONG("외삼동", Gu.YUSEONG_GU),
    BANSEOK_DONG("반석동", Gu.YUSEONG_GU),
    SINSEONG_DONG("신성동", Gu.YUSEONG_GU),
    GAJEONG_DONG("가정동", Gu.YUSEONG_GU),
    DORYONG_DONG("도룡동", Gu.YUSEONG_GU),
    JANG_DONG_YUSEONG_GU("장동", Gu.YUSEONG_GU),
    BANGHYEON_DONG("방현동", Gu.YUSEONG_GU),
    HWAAM_DONG("화암동", Gu.YUSEONG_GU),
    DEOKJIN_DONG("덕진동", Gu.YUSEONG_GU),
    CHUMOK_DONG("추목동", Gu.YUSEONG_GU),
    JAUN_DONG("자운동", Gu.YUSEONG_GU),
    SINBONG_DONG("신봉동", Gu.YUSEONG_GU),
    JEONMIN_DONG("전민동", Gu.YUSEONG_GU),
    MUNJI_DONG("문지동", Gu.YUSEONG_GU),
    WONCHON_DONG("원촌동", Gu.YUSEONG_GU),
    BONGSAN_DONG("봉산동", Gu.YUSEONG_GU),
    SONGGANG_DONG("송강동", Gu.YUSEONG_GU),
    GEUMGO_DONG("금고동", Gu.YUSEONG_GU),
    DAE_DONG_YUSEONG_GU("대동", Gu.YUSEONG_GU),
    GEUMTAN_DONG("금탄동", Gu.YUSEONG_GU),
    SIN_DONG("신동", Gu.YUSEONG_GU),
    DUNGOK_DONG("둔곡동", Gu.YUSEONG_GU),
    GURYEONG_DONG("구룡동", Gu.YUSEONG_GU),
    GWANPYEONG_DONG("관평동", Gu.YUSEONG_GU),
    YONGSAN_DONG("용산동", Gu.YUSEONG_GU),
    TAPRIP_DONG("탑립동", Gu.YUSEONG_GU),

    // 대덕구 (Daedeok-gu)
    OJEONG_DONG("오정동", Gu.DAEDEOK_GU),
    DAEHWA_DONG("대화동", Gu.DAEDEOK_GU),
    EUPNAE_DONG("읍내동", Gu.DAEDEOK_GU),
    YEONCHUK_DONG("연축동", Gu.DAEDEOK_GU),
    SINDAE_DONG("신대동", Gu.DAEDEOK_GU),
    WA_DONG("와동", Gu.DAEDEOK_GU),
    JANG_DONG_DAEDEOK_GU("장동", Gu.DAEDEOK_GU),
    BIRAE_DONG("비래동", Gu.DAEDEOK_GU),
    SONGCHON_DONG("송촌동", Gu.DAEDEOK_GU),
    JUNGNI_DONG("중리동", Gu.DAEDEOK_GU),
    BEOB_DONG("법동", Gu.DAEDEOK_GU),
    SINTANJIN_DONG("신탄진동", Gu.DAEDEOK_GU),
    SAMJEONG_DONG_DAEDEOK_GU("삼정동", Gu.DAEDEOK_GU),
    YONGHO_DONG("용호동", Gu.DAEDEOK_GU),
    IHYEON_DONG("이현동", Gu.DAEDEOK_GU),
    GALJEON_DONG("갈전동", Gu.DAEDEOK_GU),
    BUSU_DONG("부수동", Gu.DAEDEOK_GU),
    HWANGHO_DONG("황호동", Gu.DAEDEOK_GU),
    MIHO_DONG("미호동", Gu.DAEDEOK_GU),
    SEOKBONG_DONG("석봉동", Gu.DAEDEOK_GU),
    DEOKAM_DONG("덕암동", Gu.DAEDEOK_GU),
    SANGSEO_DONG("상서동", Gu.DAEDEOK_GU),
    PYEONGCHON_DONG_DAEDEOK_GU("평촌동", Gu.DAEDEOK_GU),
    MOKSANG_DONG("목상동", Gu.DAEDEOK_GU),
    MUNPYEONG_DONG("문평동", Gu.DAEDEOK_GU),
    SINIL_DONG("신일동", Gu.DAEDEOK_GU);

    private final String koreanName;
    private final Gu gu;

    public static List<Beopjeongdong> findAllByGu(Gu gu) {
        return Arrays.stream(Beopjeongdong.values())
                .filter(dong -> dong.getGu() == gu)
                .collect(Collectors.toList());
    }

    @JsonCreator
    public static Beopjeongdong from(String value) {
        if (value == null) return null;
        String upperValue = value.toUpperCase();
        for (Beopjeongdong dong : Beopjeongdong.values()) {
            if (dong.name().equals(upperValue) || dong.getKoreanName().equals(value)) {
                return dong;
            }
        }
        throw new IllegalArgumentException("알 수 없는 Beopjeongdong 값입니다: " + value);
    }
}