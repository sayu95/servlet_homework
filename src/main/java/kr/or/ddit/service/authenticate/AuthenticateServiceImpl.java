package kr.or.ddit.service.authenticate;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.or.ddit.dto.MemberDTO;
import kr.or.ddit.mapper.MemberMapper;
import kr.or.ddit.service.exception.UserNotFoundException;
import kr.or.ddit.mybatis.MapperProxyFactory;

public class AuthenticateServiceImpl implements AuthenticateService {

	private MemberMapper mapper = MapperProxyFactory.generataProxy(MemberMapper.class);
	private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	@Override
	public MemberDTO authenticate(String username, String password) {

		MemberDTO saved = mapper.selectMemberForAuth(username);

		if (saved != null) {
			String savedPass = saved.getMemPass();
			if (passwordEncoder.matches(password, savedPass)) {
				return saved;
			} else {
				// 비밀번호 오류
				throw new BadCrendentialException("비밀번호 오류");
			}
		} else {
			// 사용자 없음.
			throw new UserNotFoundException(username);
		}
	}

}
