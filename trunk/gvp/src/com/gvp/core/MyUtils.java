package com.gvp.core;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class MyUtils {
	
	
	/**
	 * 判断字符串是否为空 not null  not ""
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if("".equals(str) || str == null){
			return true;
		}
		return false;
	}
	
	/**
	 * 获得异常堆栈信息
	 * @param e
	 * @return
	 */
	public static String getExceptionMessages(Exception e){
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		e.printStackTrace(new PrintWriter(buf, true));
		return buf.toString();   
	}
	

	

	public static String formatDateToString(Date data, String format) {
		return new SimpleDateFormat(format).format(data);
	}

	/**
	 * 这个是一个递归方法,可以把集合里面所有的逻辑条件组成一个大的逻辑条件返回,最终返回的集合中只包含一个逻辑<br/> Example:
	 * 
	 * <pre>
	 * String[] conditions = MyUtils.split(page.getCondition(), " ,;");
	 * DetachedCriteria dc = DetachedCriteria.forClass(Press.class);
	 * List SimpleExpression&gt; els = new ArrayList SimpleExpression&gt;();
	 * List LogicalExpression&gt; ors = new ArrayList LogicalExpression&gt;();
	 * for (String condition : conditions) {
	 * 	els.add(Restrictions.like("issn", "%" + condition + "%"));
	 * 	els.add(Restrictions.like("pressCode", "%" + condition + "%"));
	 * 	els.add(Restrictions.like("pressName", "%" + condition + "%"));
	 * 	els.add(Restrictions.like("pressCycle", "%" + condition + "%"));
	 * 	els.add(Restrictions.like("publicationLocation", "%" + condition + "%"));
	 * 	els.add(Restrictions.like("categories", "%" + condition + "%"));
	 * 	els.add(Restrictions.like("publicationDate", "%" + condition + "%"));
	 * 	els.add(Restrictions.like("remark", "%" + condition + "%"));
	 * 	els.add(Restrictions.like("pressType", "%" + condition + "%"));
	 * }
	 * 
	 * ors = MyUtils.makeCondition(els, ors);
	 * if (ors != null) {
	 * 	dc.add(ors.iterator().next());
	 * }
	 * </pre>
	 * 
	 * @param els
	 * @param ors
	 * @return
	 */
	public static List<LogicalExpression> makeCondition(List<SimpleExpression> els, List<LogicalExpression> ors) {
		if (els != null) {
			for (Iterator<SimpleExpression> it = els.iterator(); it.hasNext();) {
				SimpleExpression s1 = null,s2 = null;
				if(it.hasNext()){
					s1 = it.next();
				}
				if(it.hasNext()){
					s2 = it.next();
				}
				if(s1 != null && s2 != null){
					ors.add(Restrictions.or(s1,s2));
				}else{
					if(s1 != null){
						ors.add(Restrictions.or(s1, s1));
					}
					if(s2 != null){
						ors.add(Restrictions.or(s2, s2));
					}
				}
			}
			els = null;
		}
		List<LogicalExpression> lees = new ArrayList<LogicalExpression>();
		for (Iterator<LogicalExpression> let = ors.iterator(); let.hasNext();) {
			LogicalExpression l1 = null,l2 = null;
			if (let.hasNext()) {
				l1 = let.next();
			}
			if (let.hasNext()) {
				l2 = let.next();
			}
			if (l1 != null && l2 != null) {
				lees.add(Restrictions.or(l1, l2));
			} else {
				if (l1 != null) {
					lees.add(l1);
				}
				if (l2 != null) {
					lees.add(l2);
				}
			}
		}
		if (lees.size() == 1) {
			return lees;
		}
		if (lees.size() == 0) {
			return null;
		}
		return MyUtils.makeCondition(els, lees);
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static boolean delFile(String filePathAndName) {
		File myDelFile = new java.io.File(filePathAndName);
		if (!myDelFile.exists()) {
			return true;
		}
		return myDelFile.delete();
	}

	/**
	 * 上传文件
	 * 
	 * @param savePath
	 *            文件的保存路径
	 * @param uploadFile
	 *            被上传的文件
	 * @return newFileName
	 */
	public static String upload(String uploadFileName, String savePath, File uploadFile) {
		String newFileName = getUUIDName(uploadFileName, savePath);
		try {
			FileOutputStream fos = new FileOutputStream(savePath + newFileName);
			FileInputStream fis = new FileInputStream(uploadFile);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newFileName;
	}
	


	/**
	 * 根据路径创建一系列的目录
	 * 
	 * @param path
	 */
	public static boolean mkDirectory(String path) {
		File file = null;
		try {
			file = new File(path);
			if (!file.exists()) {
				return file.mkdirs();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			file = null;
		}
		return false;
	}

	

	/**
	 * 删除指定文件路径下面的所有文件和文件夹
	 * 
	 * @param file
	 */
	public static boolean delFiles(String fileName) {
		boolean flag = false;
		try {
			File file = new File(fileName);
			if (file.exists()) {
				if (file.isDirectory()) {
					String[] contents = file.list();
					for (int i = 0; i < contents.length; i++) {
						delFiles(file.getAbsolutePath() + "/" + contents[i]);
					}
				}
				flag = file.delete();
			} else {
				throw new RuntimeException("File not exist!");
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	
	/**
	 * 将对象数组的每一个元素分别添加到指定集合中,调用Apache commons collections 中的方法
	 * 
	 * @param collection
	 *            目标集合对象
	 * @param arr
	 *            对象数组
	 */
	public static ArrayList addToCollection(ArrayList list, Object[] arr) {
		if (null != list && null != arr) {
			CollectionUtils.addAll(list, arr);
		}
		return list;
	}

	/**
	 * 将字符串已多个分隔符拆分为数组,调用Apache commons lang 中的方法
	 * 
	 * <pre>
	 *      Example:
	 *       String[] arr = StringUtils.split("a b,c d,e-f", " ,-");
	 *       System.out.println(arr.length);//输出6
	 * </pre>
	 * 
	 * @param str
	 *            目标字符串
	 * @param separatorChars
	 *            分隔符字符串
	 * @return 字符串数组
	 */
	public static String[] split(String str, String separatorChars) {
		return StringUtils.split(str, separatorChars);
	}

	public static Object copyProperties(Object target, Object o) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Field[] ofields = o.getClass().getDeclaredFields();
		for (Field f : ofields) {
			Object value = PropertyUtils.getProperty(o, f.getName());
			if (value != null) {
				PropertyUtils.setProperty(target, f.getName(), value);
			}
		}
		return target;
	}


	/**
	 * 调用指定字段的setter方法
	 * 
	 * <pre>
	 *      Example:
	 *      User user = new User();
	 *      MyUtils.invokeSetMethod("userName", user, new Object[] {"张三"});
	 * </pre>
	 * 
	 * @param fieldName
	 *            字段(属性)名称
	 * @param invokeObj
	 *            被调用方法的对象
	 * @param args
	 *            被调用方法的参数数组
	 * @return 成功与否
	 */
	public static boolean invokeSetMethod(String fieldName, Object invokeObj, Object[] args) {
		boolean flag = false;
		Field[] fields = invokeObj.getClass().getDeclaredFields(); // 获得对象实体类中所有定义的字段
		Method[] methods = invokeObj.getClass().getDeclaredMethods(); // 获得对象实体类中所有定义的方法
		for (Field f : fields) {
			String fname = f.getName();
			if (fname.equals(fieldName)) {// 找到要更新的字段名
				String mname = "set" + (fname.substring(0, 1).toUpperCase() + fname.substring(1));// 组建setter方法
				for (Method m : methods) {
					String name = m.getName();
					if (mname.equals(name)) {
						// 处理Integer参数
						if (f.getType().getSimpleName().equalsIgnoreCase("integer") && args.length > 0) {
							args[0] = Integer.valueOf(args[0].toString());
						}
						// 处理Boolean参数
						if (f.getType().getSimpleName().equalsIgnoreCase("boolean") && args.length > 0) {
							args[0] = Boolean.valueOf(args[0].toString());
						}
						try {
							m.invoke(invokeObj, args);
							flag = true;
						} catch (IllegalArgumentException e) {
							flag = false;
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							flag = false;
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							flag = false;
							e.printStackTrace();
						}
					}
				}
			}
		}
		return flag;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param fileName
	 * @param dir
	 * @return
	 */
	public static boolean isFileExist(String fileName, String dir) {
		File files = new File(dir + fileName);
		return (files.exists()) ? true : false;
	}

	/**
	 * 获得随机文件名,保证在同一个文件夹下不同名
	 * 
	 * @param fileName
	 * @param dir
	 * @return
	 */
	public static String getRandomName(String fileName, String dir) {
		String[] split = fileName.split("\\.");// 将文件名已.的形式拆分
		String extendFile = "." + split[split.length - 1].toLowerCase(); // 获文件的有效后缀

		Random random = new Random();
		int add = random.nextInt(1000000); // 产生随机数10000以内
		String ret = add + extendFile;
		while (isFileExist(ret, dir)) {
			add = random.nextInt(1000000);
			ret = fileName + add + extendFile;
		}
		return ret;
	}

	public static String getUUIDName(String fileName, String dir) {
		String[] split = fileName.split("\\.");
		String extendFile = "." + split[split.length - 1].toLowerCase();
		return java.util.UUID.randomUUID().toString() + extendFile;
	}

	/**
	 * 创建缩略图
	 * 
	 * @param file
	 *            上传的文件流
	 * @param height
	 *            最小的尺寸
	 * @throws IOException
	 */
	public static void createMiniPic(File file, float width, float height) throws IOException {
		Image src = javax.imageio.ImageIO.read(file); // 构造Image对象
		int old_w = src.getWidth(null); // 得到源图宽
		int old_h = src.getHeight(null);
		int new_w = 0;
		int new_h = 0; // 得到源图长
		float tempdouble;
		if (old_w >= old_h) {
			tempdouble = old_w / width;
		} else {
			tempdouble = old_h / height;
		}

		if (old_w >= width || old_h >= height) { // 如果文件小于锁略图的尺寸则复制即可
			new_w = Math.round(old_w / tempdouble);
			new_h = Math.round(old_h / tempdouble);// 计算新图长宽
			while (new_w > width && new_h > height) {
				if (new_w > width) {
					tempdouble = new_w / width;
					new_w = Math.round(new_w / tempdouble);
					new_h = Math.round(new_h / tempdouble);
				}
				if (new_h > height) {
					tempdouble = new_h / height;
					new_w = Math.round(new_w / tempdouble);
					new_h = Math.round(new_h / tempdouble);
				}
			}
			BufferedImage tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(src, 0, 0, new_w, new_h, null); // 绘制缩小后的图
			FileOutputStream newimage = new FileOutputStream(file); // 输出到文件流
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(tag);
			param.setQuality((float) (100 / 100.0), true);// 设置图片质量,100最大,默认70
			encoder.encode(tag, param);
			encoder.encode(tag); // 将JPEG编码
			newimage.close();
		}
	}

	/**
	 * 判断文件类型是否是合法的,就是判断allowTypes中是否包含contentType
	 * 
	 * @param contentType
	 *            文件类型
	 * @param allowTypes
	 *            文件类型列表
	 * @return 是否合法
	 */
	public static boolean isValid(String contentType, String[] allowTypes) {
		if (null == contentType || "".equals(contentType)) {
			return false;
		}
		for (String type : allowTypes) {
			if (contentType.equals(type)) {
				return true;
			}
		}
		return false;
	}


	public static boolean isEmpty(String[] arr) {
		if(null == arr)return true;
		return false;
	}

	
	
}
