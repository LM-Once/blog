package com.it.blog.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it.blog.entity.User;
import com.it.blog.mapper.UserMapper;
import com.it.blog.service.impl.UserServiceImpl;


@Service
public class UserService implements UserServiceImpl{


	@Value(value="classpath:uri.json")
	private Resource resource;

	private static Log logger = LogFactory.getLog(UserService.class);
	
	@Autowired 
	private UserMapper usermapper;
	
	@Override
	public List<User> queryUserList() {
		
		return usermapper.queryUserList();
	}

	@Override
	@Transactional
	/**
	 *测试 保存用户 事物混滚
	 */
	public Map<String, Object> saveUser() {
		Map<String, Object> map = null;
		try {
			map = new HashMap<String,Object>();
			usermapper.saveUser(new User("涵涵",12,0));
			usermapper.saveUser(new User("辰辰",15,1));
			usermapper.saveUser(new User("羽西",12,0));
			//int[] intArray = {1,2};
			//System.out.println(intArray[3]);
			map.put("code", 1);
			map.put("msg", "SUCCESS");
			
		} catch (Exception e) {
			throw new RuntimeException("保存失败");
		}
		return map;
	}

	@Override
	public User queryUserInfoByName(User user) {

		return usermapper.queryUserInfoByName(user);
	}

	public String getUriJson(){
		try {
			InputStream file = resource.getInputStream();
			String jsonData = this.jsonRead(file);
//            System.out.println(jsonData);
			return jsonData;

		} catch (Exception e) {
			System.out.println("uri load fail!");
 			return null;
		}
	}

	/**
	 *     读取文件类容为字符串
	 * @param file
	 * @return
	 */
	private String jsonRead(InputStream file){
		Scanner scanner = null;
		StringBuilder buffer = new StringBuilder();
		try {
			scanner = new Scanner(file, "utf-8");
			while (scanner.hasNextLine()) {
				buffer.append(scanner.nextLine());
			}
		} catch (Exception e) {
			logger.error("");
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		return buffer.toString();
	}

	@Value(value="classpath:mapping/UserMapping.xml")
	private Resource data;

	public String getApplicationYml(){
		String ymlData =null;
		try {
			InputStream file = data.getInputStream();
			ymlData = this.getData(file);

		} catch (IOException e) {
			e.printStackTrace();
			logger.error("读取数据失败！");
		}
		return ymlData;
	}

	public String getData(InputStream file){
		Scanner scanner =null;
		StringBuffer sb = new StringBuffer();
		try {
			scanner = new Scanner(file,"UTF-8");
			while (scanner.hasNextLine()){
				sb.append(scanner.nextLine());
			}
		} catch (Exception e) {
			logger.error("读取数据失败！");
			e.printStackTrace();
		} finally {
			if (scanner!=null){
				scanner.close();
			}
		}
		return sb.toString();
	}

	@Value(value="classpath:mapping/UserMapping.xml")
	private Resource file;

	public String getXmlfile(){
		Scanner scanner = null;
		StringBuffer sb = null;
		try {
			InputStream is =file.getInputStream();
			scanner = new Scanner(is,"UTF-8");
			sb = new StringBuffer();
			while (scanner.hasNextLine()){

				sb.append(scanner.nextLine());
			}
		}catch (Exception e){
			logger.error("读取文件错误",e);
		}finally {
			if (scanner!=null){
				scanner.close();
			}
		}
		logger.info(sb.toString());
		return sb.toString();
	}


}
