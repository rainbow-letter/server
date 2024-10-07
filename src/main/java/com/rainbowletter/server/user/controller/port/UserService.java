package com.rainbowletter.server.user.controller.port;

import com.rainbowletter.server.common.dto.TokenResponse;
import com.rainbowletter.server.user.dto.UserChangePassword;
import com.rainbowletter.server.user.dto.UserChangePhoneNumber;
import com.rainbowletter.server.user.dto.UserCreate;
import com.rainbowletter.server.user.dto.UserFindPassword;
import com.rainbowletter.server.user.dto.UserInformationResponse;
import com.rainbowletter.server.user.dto.UserLogin;
import com.rainbowletter.server.user.dto.UserResetPassword;

public interface UserService {

	UserInformationResponse information(String email);

	UserInformationResponse information(Long id);

	Long create(UserCreate userCreate);

	TokenResponse login(UserLogin userLogin);

	void findPassword(UserFindPassword userFindPassword);

	void resetPassword(String email, UserResetPassword userResetPassword);

	void changePassword(String email, UserChangePassword userChangePassword);

	void changePhoneNumber(String email, UserChangePhoneNumber userChangePhoneNumber);

	void deletePhoneNumber(String email);

	void leave(String email);

}
