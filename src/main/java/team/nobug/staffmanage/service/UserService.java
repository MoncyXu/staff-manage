package team.nobug.staffmanage.service;

import javax.servlet.http.HttpSession;

import team.nobug.staffmanage.pojo.Message;
import team.nobug.staffmanage.pojo.User;

/**
 * �û�����ӿ�
 * @author ��ï��
 *
 */
public interface UserService {

	Message checkLogin(String tel, String pwd);

	Message updateUser(User user, HttpSession session);

	Message changePwd(String pwd, String newpwd, String repwd, HttpSession session);

}
