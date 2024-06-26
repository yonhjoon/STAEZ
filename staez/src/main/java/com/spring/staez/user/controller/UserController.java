package com.spring.staez.user.controller;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.staez.user.model.vo.User;
import com.spring.staez.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService userService;
	private final BCryptPasswordEncoder bcryptPasswordEncoder;
	private final JavaMailSender sender;

	// 로그인
	@GetMapping("loginForm.me")
	public String loginForm() {
		return "user/loginForm";
	}

	// 회원가입
	@GetMapping("insertForm.me")
	public String signinForm() {
		return "user/insertForm";
	}

	// 아이디찾기
	@GetMapping("findIdForm.me")
	public String findIdForm() {
		return "user/findIdForm";
	}

	// 아이디찾기결과
	@GetMapping("idListForm.me")
	public String idListForm() {
		return "user/idListForm";
	}

	// 비밀번호 찾기
	@GetMapping("findPwdForm.me")
	public String findPwdForm() {
		return "user/findPwdForm";
	}
	
	// 아이디체크
	@ResponseBody
	@GetMapping("idCheck.me")
	public String idCheck(String checkId) {
		int result = userService.idCheck(checkId);

		if (result > 0) { // 이미 존재한다면
			return "NNNNN";
		} else { // 존재하지 않는다면
			return "NNNNY";
		}
	}

	// 닉네임체크
	@ResponseBody
	@GetMapping("nickCheck.me")
	public String nickCheck(String checkNick) {
		int result = userService.nickCheck(checkNick);

		if (result > 0) { // 이미 존재한다면
			return "NNNNN";
		} else { // 존재하지 않는다면
			return "NNNNY";
		}
	}

	// 회원가입
	@PostMapping("insert.me")
	public String insertMember(User u, HttpSession session, Model model) {

		// 비밀번호가 null인지 확인
		if (u.getUserPwd() == null) {
			model.addAttribute("alertMsg", "비밀번호가 입력되지 않았습니다.");
			return "user/insertForm";
		}

		// 암호화 작업
		String encPwd = bcryptPasswordEncoder.encode(u.getUserPwd());
		u.setUserPwd(encPwd);

		try {
			int result = userService.insertUser(u);

			if (result > 0) {
				session.setAttribute("alertMsg", "성공적으로 회원가입이 완료되었습니다.");
				return "redirect:/";
			} else {
				model.addAttribute("alertMsg", "회원가입 실패");
				return "user/insertForm";
			}
		} catch (DataIntegrityViolationException e) {
			// 제약 조건 위반 예외 처리
			String errorMessage = "회원가입을 실패하였습니다 : " + e.getRootCause().getMessage();
			session.setAttribute("alertMsg", errorMessage);
			return "user/insertForm";
		}
	}
	
	// 회원가입 시 핸드폰 체크
	@ResponseBody
	@GetMapping("insertPhoneCheck.me")
	public String insertPhoneCheck(String checkPhone) {
		int result = userService.insertPhoneCheck(checkPhone);

		if (result > 0) { // 이미 존재한다면
			System.out.println("onPhone : "+result);
			return "onPhone";
		} else { // 존재하지 않는다면
			System.out.println("NoPhone : "+result);
			return "NoPhone";
		}
	}
	
	// 이메일 체크 (이메일/UUID/등록날짜 등록해야댐)
    @ResponseBody
    @GetMapping("emailCheck.me")
    public String emailCheck(String email) {
        
        // 이메일 중복 체크
        User user = userService.emailCheck(email);
        if (user != null) {
            return "emailCheck Duplicated"; // 이메일 중복일 경우
            
        } else {
        	// UUID 생성
            String shortenedAuthNo = UUID.randomUUID().toString();
            String authNo = shortenedAuthNo.substring(0, 6);

            // 메일 전송
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("STAEZ 이메일 인증 번호");
            message.setText("STAEZ 인증 번호: " + authNo);

            try {
                sender.send(message); // 이메일 전송 시도

                // 이메일 존재 여부 확인
                Map<String, Object> existingEmail = userService.findEmail(email);

                int result;
                if (existingEmail == null) {
                    // 이메일이 없으면 등록
                    result = userService.registerUser(email, authNo);
                } else {
                    // 이메일이 있으면 업데이트
                    result = userService.updateEmailAuth(email, authNo);
                }

                if (result > 0) { // 저장 또는 업데이트 완료
                    return "emailCheck Yes";
                } else { // 저장 또는 업데이트 실패
                    return "emailCheck No";
                }
            } catch (MailException e) {
                System.out.println("이메일 전송 실패: " + e.getMessage());
                return "emailCheck No";
            }
        }
       }

    // 아이디 찾기 중 이메일과 이름이 일치하는지
    @ResponseBody
    @GetMapping("emailbyName.me")
    public String emailbyIdCheck(@RequestParam("checkEmail") String checkEmail, @RequestParam("userName") String userName) {
        int result = userService.emailbyIdCheck(checkEmail, userName);

        if (result > 0) { // 이미 존재한다면
            // UUID 생성
            String shortenedAuthNo = UUID.randomUUID().toString();
            String authNo = shortenedAuthNo.substring(0, 6);

            // 메일 전송
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(checkEmail);
            message.setSubject("STAEZ 이메일 인증 번호");
            message.setText("STAEZ 인증 번호: " + authNo);

            try {
                sender.send(message); // 이메일 전송 시도
                System.out.println("이메일 전송 성공");

                // 이메일 존재 여부 확인
                Map<String, Object> existingEmail = userService.findEmail(checkEmail);

                int resultEmail;
                if (existingEmail == null) {
                    // 이메일이 없으면 등록
                    resultEmail = userService.registerUser(checkEmail, authNo);
                } else {
                    // 이메일이 있으면 업데이트
                    resultEmail = userService.updateEmailAuth(checkEmail, authNo);
                }

                if (resultEmail > 0) { // 저장 또는 업데이트 완료
                    return "emailCheck Yes";
                } else { // 저장 또는 업데이트 실패
                    return "emailCheck No";
                }
            } catch (MailException e) {
                System.out.println("이메일 전송 실패: " + e.getMessage());
                return "emailCheck No";
            }
        } else { // 존재하지 않는다면
            return "emailCheck Invalid";
        }
    }
    
    // 비밀번호 찾기 중 이메일과 이름과 핸드폰번호가 일치하는지
    @ResponseBody
    @GetMapping("emailbyNamebyPhone.me")
    public String emailbyNamebyPhone(@RequestParam("checkEmail") String checkEmail, @RequestParam("userName") String userName, @RequestParam("phone") String phone) {
        System.out.println("Received email: " + checkEmail + ", name: " + userName + ", phone: " + phone);

        int result = userService.emailbyNamebyPhone(checkEmail, userName, phone);

        if (result > 0) {
            String shortenedAuthNo = UUID.randomUUID().toString();
            String authNo = shortenedAuthNo.substring(0, 6);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(checkEmail);
            message.setSubject("STAEZ 이메일 인증 번호");
            message.setText("STAEZ 인증 번호: " + authNo);

            try {
                sender.send(message);
                System.out.println("이메일 전송 성공");

                Map<String, Object> existingEmail = userService.findEmail(checkEmail);

                int resultEmail;
                if (existingEmail == null) {
                    resultEmail = userService.registerUser(checkEmail, authNo);
                } else {
                    resultEmail = userService.updateEmailAuth(checkEmail, authNo);
                }

                if (resultEmail > 0) {
                    return "emailCheck Yes";
                } else {
                    return "emailCheck No";
                }
            } catch (MailException e) {
                System.out.println("이메일 전송 실패: " + e.getMessage());
                return "emailCheck No";
            }
        } else {
            return "emailCheck Invalid";
        }
    }


	// 이메일 암호키 인증체크
	@ResponseBody
	@GetMapping("emailSecretCodeCheck.me")
	public String uuidCheck(@RequestParam("authNo") String authNo, @RequestParam("email") String email) {
		int result = userService.emailSecretCodeCheck(authNo, email);
		if (result > 0) { // 일치하다면
			return "emailSecretCodeCheck Yes"; // 수정된 부분
		} else { // 일치하지 않다면
			return "emailSecretCodeCheck No"; // 수정된 부분
		}
	}

	// 이메일로 아이디찾기
	@ResponseBody
	@GetMapping("findEmailCheck.me")
	public String findEmailCheck(@RequestParam("checkFindEmail") String checkFindEmail, @RequestParam("userName") String userName) {
	    String result = userService.findEmailCheck(checkFindEmail, userName);
	    return result;
	}

	// 핸드폰으로 아이디찾기
	@ResponseBody
	@GetMapping("findPhoneCheck.me")
	public String findPhoneCheck(@RequestParam("checkFindPhone") String checkFindPhone, @RequestParam("userName") String userName, HttpSession session) {
	    String result = userService.findPhoneCheck(checkFindPhone, userName);
	    if(result != null) {
	        return result;
	    } else {
	        return "findPhoneCheck No";
	    }
	}

	// 비밀번호 찾기 새로운 비밀번호 업데이트
    @PostMapping("checkFindNewPwd.me")
    @ResponseBody
    public String checkFindNewPwd(@RequestParam("userId") String user_id, @RequestParam("phone") String phone,
        @RequestParam("email") String email,  HttpSession session) {
        
        // 유효성 검사 및 사용자 정보 확인
        String user = userService.findUserByIdEmailPhone(user_id, phone, email);

        if (user != null) {
            // 사용자 정보를 찾았을 경우
            // 세션으로 유저 정보 저장
            session.setAttribute("user_id", user_id);
            session.setAttribute("phone", phone);
            session.setAttribute("email", email);
            return "New Pwd Go";
        } else {
            // 사용자 정보를 찾지 못했을 경우
            return "Funk New Pwd";
        }
    }

	// 새로운 비밀번호 저장
	@ResponseBody
	@PostMapping("insertNewPwd.me")
	public String insertNewPwd(String newPassword, HttpSession session, Model model) {
	  
	    // 세션에서 유저 정보를 가져온다
	    String user_id = (String) session.getAttribute("user_id");
	    String phone = (String) session.getAttribute("phone");
	    String email = (String) session.getAttribute("email");

	    // 암호화 작업
	    String encPwd = bcryptPasswordEncoder.encode(newPassword);

	    // 새로운 비밀번호와 함께 유저 정보를 업데이트
	    int result = userService.updatePassword(user_id, phone, email, encPwd);

	    if (result > 0) {
	        return "Password Changed";
	    } else {
	        return "no";
	    }
	}

	// 이메일 인증 완료하면 정보 업데이트(테이블에 이메일 데이터 있을때)
	@ResponseBody
	@GetMapping("updateEmailAuth.me")
	public String deleteEmailAuth(@RequestParam("email") String email, @RequestParam("authNo") String authNo) {

		int result = 0;
		result = userService.updateEmailAuth(email, authNo);

		if (result == 0) {
			// 사용자 정보를 찾았을 경우
			result = userService.registerUser(email, authNo);
			return "insert email ";
		} else {
			// 사용자 정보를 찾지 못했을 경우
			return "Fuck email";
		}
	}

}