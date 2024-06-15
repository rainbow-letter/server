package com.rainbowletter.server.notification.domain;

import com.rainbowletter.server.common.exception.RainbowLetterException;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlimTalkTemplateImpl implements AlimTalkTemplate {
	REPLY("TT_1758") {
		@Override
		public String subject(final Object... args) {
			validate(getCode(), 0, args);
			return "[무지개편지] 편지답장 도착 알림";
		}

		@Override
		public String failSubject(final Object... args) {
			validate(getCode(), 0, args);
			return "[무지개편지]";
		}

		@Override
		public String content(final Object... args) {
			validate(getCode(), 4, args);
			return """
					[무지개편지] 편지답장 도착 알림
					\s
					안녕하세요,
					%s %s님!
					%s, %s에게 쓴 답장이 도착했어요! : )
					\s
					아래 '답장 보러 가기' 버튼을 누르시면 편지함으로 이동합니다.
					\s
					무지개편지, 잘 이용하고 계신가요?
					서비스 만족도 평가를 남겨주시면 무지개편지가 발전하는데 큰 힘이 됩니다.
					""".formatted(args);
		}

		@Override
		public String failContent(final Object... args) {
			validate(getCode(), 2, args);
			return """
					[무지개편지]
					\s
					%s, %s에게 쓴 답장이 도착했어요! : )
					\s
					편지함에서 확인해주세요!
					""".formatted(args);
		}

		@Override
		public List<AlimTalkButton> buttons(final Object... args) {
			validate(getCode(), 1, args);
			final var button1 = AlimTalkButton.createWebLink("답장 보러 가기", args[0].toString());
			final var button2 = AlimTalkButton.createWebLink("만족도 조사 하러가기", "https://forms.gle/gCMBwqukEqjBnBq9A");
			final var button3 = AlimTalkButton.createBotKeyword("문의하기");
			return List.of(button1, button2, button3);
		}
	},
	DELIVERY_START("TT_1759") {
		@Override
		public String subject(final Object... args) {
			validate(getCode(), 0, args);
			return "[무지개편지] 배송 출발 안내";
		}

		@Override
		public String failSubject(final Object... args) {
			validate(getCode(), 0, args);
			return "[무지개편지] 배송 출발 안내";
		}

		@Override
		public String content(final Object... args) {
			validate(getCode(), 6, args);
			return """
					[무지개편지] 배송 출발 안내
					\s
					%s와의 추억이 담긴 %s이 출발했어요!
					\s
					아래 [배송조회] 를 누르시면 배송 위치를 확인하실 수 있어요.
					\s
					□ 주문번호 : %s
					□ 상품명 : %s
					□ 배송지 : %s
					□ 택배사 : 한진택배
					□ 운송장 번호 : %s
					""".formatted(args);
		}

		@Override
		public String failContent(final Object... args) {
			validate(getCode(), 6, args);
			return """
					[무지개편지] 배송 출발 안내
					\s
					%s와의 추억이 담긴 %s이 출발했어요!
					\s
					□ 주문번호 : %s
					□ 상품명 : %s
					□ 배송지 : %s
					□ 택배사 : 한진택배
					□ 운송장 번호 : %s
					""".formatted(args);
		}

		@Override
		public List<AlimTalkButton> buttons(final Object... args) {
			validate(getCode(), 0, args);
			final var button = AlimTalkButton.createDelivery();
			return List.of(button);
		}
	},
	PAYED("TT_1760") {
		@Override
		public String subject(final Object... args) {
			validate(getCode(), 1, args);
			return """
					%s의
					사진&편지를 보내주세요
					""".formatted(args);
		}

		@Override
		public String failSubject(final Object... args) {
			validate(getCode(), 0, args);
			return "[무지개편지] 결제 완료 안내";
		}

		@Override
		public String content(final Object... args) {
			validate(getCode(), 3, args);
			return """
					[무지개편지] 결제 완료 안내
					%s 결제가 완료되었습니다.
					\s
					아래 [사진&편지 제출하기]를 눌러,
					엽서북에 들어갈 아이의 사진과 편지를 보내주세요!
					\s
					아이의 사진과 편지를 보내주셔야 최종 주문 완료됩니다.
					\s
					□ 주문일 : %s
					□ 결제금액 : %s원
					""".formatted(args);
		}

		@Override
		public String failContent(final Object... args) {
			validate(getCode(), 3, args);
			return """
					[무지개편지] 결제 완료 안내
					%s 결제가 완료되었습니다.
					\s
					아래 [사진&편지 제출하기]를 눌러,
					엽서북에 들어갈 아이의 사진과 편지를 보내주세요!
					\s
					아이의 사진과 편지를 보내주셔야 최종 주문 완료됩니다.
					\s
					□ 주문일 : %s
					□ 결제금액 : %s원
					\s
					사진&편지 제출하기 링크 :
					https://naver.me/GzgPtvGW
					""".formatted(args);
		}

		@Override
		public List<AlimTalkButton> buttons(final Object... args) {
			validate(getCode(), 0, args);
			final var button1 = AlimTalkButton.createWebLink("사진&편지 제출하기", "https://naver.me/GzgPtvGW");
			final var button2 = AlimTalkButton.createMessageServe("상품 문의하기");
			return List.of(button1, button2);
		}
	},
	PHOTO("TT_1761") {
		@Override
		public String subject(final Object... args) {
			validate(getCode(), 1, args);
			return """
					%s의
					사진&편지를 보내주세요
					""".formatted(args);
		}

		@Override
		public String failSubject(final Object... args) {
			validate(getCode(), 0, args);
			return "[무지개편지] 엽서북 제작을 위한 사진&편지 제출 안내";
		}

		@Override
		public String content(final Object... args) {
			validate(getCode(), 2, args);
			return """
					[무지개편지]
					엽서북 제작을 위한 사진&편지 제출 안내
					\s
					%s의 사진과 편지가 도착하지 않아 엽서북을 만들지 못하고 있어요.
					\s
					%s까지 제출해주지 않으면 결제가 취소돼요.
					\s
					아래 [사진&편지 제출하기] 를 눌러 제출해주세요!
					""".formatted(args);
		}

		@Override
		public String failContent(final Object... args) {
			validate(getCode(), 2, args);
			return """
					[무지개편지]
					엽서북 제작을 위한 사진&편지 제출 안내
					\s
					%s의 사진과 편지가 도착하지 않아 엽서북을 만들지 못하고 있어요.
					\s
					%s까지 제출해주지 않으면 결제가 취소돼요.
					\s
					아래 [사진&편지 제출하기] 를 눌러 제출해주세요!
					\s
					사진&편지 제출하기 :
					https://naver.me/GzgPtvGW
					""".formatted(args);
		}

		@Override
		public List<AlimTalkButton> buttons(final Object... args) {
			validate(getCode(), 0, args);
			final var button1 = AlimTalkButton.createWebLink("사진&편지 제출하기", "https://naver.me/GzgPtvGW");
			final var button2 = AlimTalkButton.createMessageServe("상품 문의하기");
			return List.of(button1, button2);
		}
	};

	private final String code;

	private static void validate(final String code, final int length, final Object... args) {
		if (args.length != length) {
			throw new RainbowLetterException("알림톡 템플릿 필수 파라미터 %d개가 필요합니다. 현재 %d개".formatted(length, args.length), code);
		}
	}

}
