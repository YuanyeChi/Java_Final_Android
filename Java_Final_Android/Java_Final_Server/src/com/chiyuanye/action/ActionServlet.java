package com.chiyuanye.action;

import com.chiyuanye.entity.BaseResult;
import com.chiyuanye.entity.CheckUpdate;
import com.chiyuanye.entity.User;
import com.chiyuanye.entity.WholeAnswer;
import com.chiyuanye.util.SQLiteJDBCUtils;
import com.chiyuanye.util.Utils;

import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.*;

/**
 * Created by ansen Create Time 2017-06-05
 */
public class ActionServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		  
		String url = request.getRequestURI();
		System.out.println(url);
		
		PrintWriter out = response.getWriter();
		// 下载
		Matcher matcher1 = Pattern.compile("(.*)(/download/)(.*)").matcher(url);
		if (matcher1.find()) {
			//获取输出流
			OutputStream downLoadOut = response.getOutputStream();
			String fileName = getServletContext().getRealPath("/download/" + matcher1.group(3));
			System.out.println(getServletContext());
			response.setContentType(getServletContext().getMimeType(fileName));
			try {
				downLoadOut = Utils.download(downLoadOut, fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				downLoadOut.close();
			}
		}
		String action = url.substring(url.lastIndexOf("/"), url.lastIndexOf("."));
		System.out.println("url:" + url + " action:" + action);
		if (action.equals("/getAnswer")) {
			WholeAnswer wholeAnswer;
			String savepath = this.getServletContext().getRealPath("/answer/");
			wholeAnswer = Utils.getAnswer(savepath);
			out.append(JSONObject.fromObject(wholeAnswer).toString());
			System.out.println(JSONObject.fromObject(wholeAnswer).toString());
		}else if(action.equals("/getComment")) {
			User user;
			String savepath = this.getServletContext().getRealPath("/answer/");
			//String userName = request.getParameter("user").substring(0, request.getParameter("user").length() - 1);
			String userName = request.getParameter("user");
			System.out.println(savepath + userName);
			user = Utils.getComment(savepath + userName);
			out.append(JSONObject.fromObject(user).toString());
			System.out.println(JSONObject.fromObject(user).toString());
		}
		out.close();
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String url = request.getRequestURI();
		String action = url.substring(url.lastIndexOf("/"), url.lastIndexOf("."));

		System.out.println("url:" + url + " action:" + action);
		PrintWriter out = response.getWriter();
		User user = new User();
		if (action.equals("/register")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			user.setUsername(username);
			user.setPassword(password);
			
			user = Utils.registerUser(user, getServletContext().getRealPath("/db/User.db"));
			if (user.getExist().equals("false"))
				System.out.println("注册成功");
			else {
				System.out.println("用户名重复");
			}
			out.append(JSONObject.fromObject(user).toString());
			System.out.println(JSONObject.fromObject(user).toString());
		
		}else if(action.equals("/login")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			user.setUsername(username);
			user.setPassword(password);
			user = Utils.checkUser(user, getServletContext().getRealPath("/db/User.db"));
			if (user.getExist().equals("true"))
				System.out.println("登录成功");
			else {
				System.out.println("登录失败");
			}
			out.append(JSONObject.fromObject(user).toString());
			System.out.println(JSONObject.fromObject(user).toString());
		}else if (action.equals("/writeAnswer")) {
			String username = request.getParameter("username");
			String answer = request.getParameter("answer");
			String fileName = request.getParameter("fileName");
			user.setUsername(username);
			user.setAnswer(answer);
			user = Utils.saveAnswer(user, getServletContext().getRealPath("/answer/"), fileName);
			out.append(JSONObject.fromObject(user).toString());
			System.out.println(JSONObject.fromObject(user).toString());
        }else if (action.equals("/writeComment")) {
			String username = request.getParameter("username");
			String comment = request.getParameter("comment");
			String fileName = request.getParameter("fileName");
			user.setUsername(username);
			user.setComment(comment);
			user = Utils.saveComment(user, getServletContext().getRealPath("/answer/"), fileName);
			//user = Utils.saveAnswer(user, getServletContext().getRealPath("/answer/"), fileName);
			out.append(JSONObject.fromObject(user).toString());
			System.out.println(JSONObject.fromObject(user).toString());
        } 
		out.close();
	}
}
