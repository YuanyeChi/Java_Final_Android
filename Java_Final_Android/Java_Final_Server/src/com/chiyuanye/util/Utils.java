package com.chiyuanye.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import javax.jws.soap.SOAPBinding.Use;

import com.chiyuanye.entity.DownloadResult;
import com.chiyuanye.entity.User;
import com.chiyuanye.entity.WholeAnswer;

/**
 * Created by  ansen
 * Create Time 2017-06-05
 */
public class Utils {
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }
    
    public static OutputStream download(OutputStream out, String filename) throws Exception {
    	InputStream in = new FileInputStream(filename);
    	DownloadResult downloadResult = new DownloadResult();
    	int b;
		while((b=in.read())!= -1)
		{
			out.write(b);
		}
		in.close();
    	return out;
    }
    
    public static User registerUser(User user, String filePath) {
    	String tableName = "LogInfo";
		SQLiteJDBCUtils userDB = new SQLiteJDBCUtils();
		userDB.before(filePath);
		
		try {
			ResultSet rs = userDB.stmt.executeQuery("SELECT * FROM LogInfo group by id having USERNAME='"+
					user.getUsername() + "';");
			if(rs.isBeforeFirst()) {
				user.setExist(true);
				user.setErrorReason("用户名重复");
			}
			else {
				user.setExist(false);
				StringBuffer insertions = new StringBuffer();
				String sql = "INSERT INTO " + tableName + "(USERNAME, PASSWORD) " +
			               "VALUES ('" + user.getUsername() + "', '" + user.getPassword() + 
			               "');\n";
				insertions.append(sql);
				userDB.insert(insertions);
				user.setErrorReason("注册成功");
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			userDB.after();
		}
		return user;
    }
    
    public static User checkUser(User user, String filePath) {
    	String tableName = "LogInfo";
		SQLiteJDBCUtils userDB = new SQLiteJDBCUtils();
		userDB.before(filePath);
		
		try {
			ResultSet rs = userDB.stmt.executeQuery("SELECT * FROM LogInfo group by id having (USERNAME='"+
					user.getUsername() + "') and (PASSWORD='" + user.getPassword()+ "');");
			if(rs.isBeforeFirst()) {
				user.setExist(true);
				user.setErrorReason("登录成功");
			}
			else {
				user.setExist(false);
				user.setErrorReason("登录失败");
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			userDB.after();
		}
		return user;
    }
    
    public static User saveAnswer(User user, String fileDir, String fileName) {
    	File userDir = new File(fileDir + user.getUsername());
    	System.out.print(fileDir + user.getUsername());
    	//第一次提交创建目录
    	if ( !userDir.exists()){
    		userDir.mkdir();
    		System.out.println("创建文件夹路径为："+ userDir);
    	}else {
    		System.out.println("已存在该用户目录");
    	}
    	fileName = userDir + "/" + fileName;
    	PrintWriter out = null;
    	try {
    		out= new PrintWriter(new File(fileName), "UTF-8");
    		//out = new PrintWriter(new File(fileName));
			out.write(user.getAnswer());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
			user.setErrorReason("上传成功");
		}   	
    	return user;
    }
    
    public static WholeAnswer getAnswer(String fileDir) {
    	File dirFile = new File(fileDir);
    	System.out.print(dirFile);
    	String[] fileList = dirFile.list();
    	WholeAnswer wholeAnswer = new WholeAnswer();
    	for (int i = 0; i < fileList.length; i++) {
			String string = fileList[i];
			//存用户名字
			wholeAnswer.setUserNames(wholeAnswer.getUserNames() + string + ";");
			//File("documentName","fileName")是File的另一个构造器
			File file = new File(dirFile.getPath(),string);
			//遍历用户文件夹
			File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
			for(File f:fs){					//遍历File[]数组
				if(!f.isDirectory()) {
					//获得文件名
					wholeAnswer.setUserAnswers(wholeAnswer.getUserAnswer() + f.getName() + ":");
					BufferedReader reader = null;
					//获得文件内容
					try {
						//注意编码
						reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
						String s = null;
						while((s = reader.readLine())!=null){
							wholeAnswer.setUserAnswers(wholeAnswer.getUserAnswer() + s);
						}
					}catch (IOException e) {
						e.printStackTrace();
					}finally {
						try {
							reader.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					wholeAnswer.setUserAnswers(wholeAnswer.getUserAnswer() + ";");
				}			
			}
			wholeAnswer.setUserAnswers(wholeAnswer.getUserAnswer() + "|");
    	}
    	return wholeAnswer;
    }
    
    public static User saveComment(User user, String fileDir, String fileName) {
    	File userDir = new File(fileDir + user.getUsername());
    	//把答案文件删除
    	File userAnswer = new File(userDir + "/" + fileName.replace("_comment", ""));
    	String myAnswer = "<h2>我的答案</h2>";
    	if(userAnswer.exists()) {
    		BufferedReader reader = null;
			try {
	    		reader = new BufferedReader(new InputStreamReader(new FileInputStream(userAnswer), "UTF-8"));
	    		String s = "";
				while((s = reader.readLine())!=null){
					myAnswer += s;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		userAnswer.delete();
    		try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	System.out.println(myAnswer);
    	fileName = userDir + "/" + fileName;
    	PrintWriter out = null;
    	try {
			out = new PrintWriter(new File(fileName), "UTF-8");
			out.write(myAnswer + "<h2>老师评语</h2>" + user.getComment());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
			user.setErrorReason("上传成功");
		}   	
    	return user;
    }

    public static User getComment(String fileDir) {
    	User user = new User();
    	File dirFile = new File(fileDir);
    	if (dirFile.exists()) {
    	String[] fileList = dirFile.list();
    	for (int i = 0; i < fileList.length; i++) {
    		if (fileList[i].contains("comment")) {
    			user.setComment(user.getComment() + fileList[i]);
    			user.setErrorReason("have comment");
    			BufferedReader reader = null;
    			System.out.println(fileList[i]);
				//获得文件内容
				try {
					//注意要是完整路径
					reader = new BufferedReader(new InputStreamReader(new FileInputStream(dirFile + "/" + fileList[i]), "UTF-8"));
					String s = "";
					while((s = reader.readLine())!=null){
						user.setComment(user.getComment() + s);
					}
				}catch (IOException e) {
					e.printStackTrace();
				}finally {
					try {
						reader.close();
						File file  = new File(dirFile + "/" + fileList[i]);
						file.delete();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
    		}
    	}} else {user.setErrorReason("have no comment");}
    	return user;
    }
}
