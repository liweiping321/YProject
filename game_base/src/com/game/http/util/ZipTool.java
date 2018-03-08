package com.game.http.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 解压缩工具
 * 
 * @author Administrator
 * 
 */
public class ZipTool {
	private static final int BUFFER = 1024;
	private int level;

	public ZipTool() {
		level = 0;
	}

	/**
	 * setLevel
	 * 
	 * @param level
	 *            int
	 */
	public void setLevel(int level) {
		this.level = level;

	}

	/**
	 * zip a File or Directory
	 * 
	 * @param inputFile
	 *            File
	 * @param outputFile
	 *            File
	 * @throws ZipException
	 */
	public void zipFile(File inputFile, File outputFile) throws ZipException {
		try {
			BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(outputFile), BUFFER);
			ZipOutputStream out = new ZipOutputStream(bout);
			zip(out, inputFile, inputFile.getName());
			out.close();
		} catch (IOException ex) {
			throw new ZipException(ex.getMessage());
		}
	}

	/**
	 * zip some Files
	 * 
	 * @param inputFiles
	 *            File[]
	 * @param outputFile
	 *            File
	 * @throws ZipException
	 */
	public void zipFiles(File[] inputFiles, File outputFile) throws ZipException {
		try {
			BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(outputFile), BUFFER);
			ZipOutputStream out = new ZipOutputStream(bout);
			for (int i = 0; i < inputFiles.length; i++) {
				zip(out, inputFiles[i], inputFiles[i].getName());
			}
			out.close();
		} catch (IOException ex) {
			throw new ZipException(ex.getMessage());
		}
	}

	/**
	 * unzip a File
	 * 
	 * @param inputFile
	 * @param outputPath
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public void unZipFile(String inputFile, String outputPath) throws Exception {
		ZipFile zipFile = new ZipFile(new File(inputFile));
		Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry zipEntry = entries.nextElement();
			String fileName = zipEntry.getName();
			File temp = new File(outputPath + File.separator + fileName);
			if (!temp.getParentFile().exists()) {
				temp.getParentFile().mkdirs();
			}
			OutputStream os = new FileOutputStream(temp);
			InputStream is = zipFile.getInputStream(zipEntry);
			int len = 0;
			while ((len = is.read()) != -1) {
				os.write(len);
			}
			os.close();
			is.close();
		}
		zipFile.close();
	}

	private void zip(ZipOutputStream out, File f, String base) throws FileNotFoundException, ZipException {
		if (level != 0) {
			out.setLevel(level);
		}
		if (f.isDirectory()) {
			zipDirectory(out, f, base);
		} else {
			if (base == null || base.equals("")) {
				base = f.getName();
			}
			zipLeapFile(out, f, base);
		}
	}

	private void zipDirectory(ZipOutputStream out, File f, String base) throws FileNotFoundException, ZipException {
		File[] files = f.listFiles();
		if (level != 0) {
			out.setLevel(level);
		}
		try {
			out.putNextEntry(new ZipEntry(base + "/"));
		} catch (IOException ex) {
			throw new ZipException(ex.getMessage());
		}
		if (base == null || base.equals("")) {
			base = new String();
		} else {
			base = base + "/";
		}
		for (int i = 0; i < files.length; i++) {
			zip(out, files[i], base + files[i].getName());
		}
	}

	private void zipLeapFile(ZipOutputStream out, File f, String base) throws FileNotFoundException, ZipException {
		if (level != 0) {
			out.setLevel(level);
		}
		try {
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			BufferedInputStream bufferIn = new BufferedInputStream(in, BUFFER);
			byte[] data = new byte[BUFFER];
			int b;
			while ((b = bufferIn.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, b);
			}
			bufferIn.close();
		} catch (IOException ex) {
			throw new ZipException(ex.getMessage());
		}
	}

	/**
	 * 解压缩JAR包
	 * 
	 * @param fileName
	 *            文件名
	 * @param outputPath
	 *            解压输出路径
	 * @throws IOException
	 *             IO异常
	 */
	public void decompress(String fileName, String outputPath) throws Exception {
		if (!outputPath.endsWith(File.separator)) {
			outputPath += File.separator;
		}
		JarFile jf = new JarFile(fileName);
		try {
			for (Enumeration<JarEntry> e = jf.entries(); e.hasMoreElements();) {
				JarEntry je = e.nextElement();
				String outFileName = outputPath + je.getName();
				File f = new File(outFileName);
				// 创建该路径的目录和所有父目录
				makeSupDir(outFileName);
				// 如果是目录，则直接进入下一个循环
				if (f.isDirectory()) {
					continue;
				}
				InputStream in = null;
				OutputStream out = null;
				try {
					in = jf.getInputStream(je);
					out = new BufferedOutputStream(new FileOutputStream(f));
					byte[] buffer = new byte[BUFFER];
					int nBytes = 0;
					while ((nBytes = in.read(buffer)) > 0) {
						out.write(buffer, 0, nBytes);
					}
				} catch (IOException ioe) {
					throw ioe;
				} finally {
					try {
						if (null != out) {
							out.flush();
							out.close();
						}
					} catch (IOException ioe) {
						throw ioe;
					} finally {
						if (null != in) {
							in.close();
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			jf.close();
		}
	}

	/**
	 * 循环创建父目录
	 * 
	 * @param outFileName
	 */
	private void makeSupDir(String outFileName) {
		// 匹配分隔符
		Pattern p = Pattern.compile("[/\\" + File.separator + "]");
		Matcher m = p.matcher(outFileName);
		// 每找到一个匹配的分隔符，则创建一个该分隔符以前的目录
		while (m.find()) {
			int index = m.start();
			String subDir = outFileName.substring(0, index);
			File subDirFile = new File(subDir);
			if (!subDirFile.exists()) {
				subDirFile.mkdir();
			}
		}
	}

	/**
	 * 使用文件通道的方式复制文件
	 * 
	 * @param s
	 *            源文件
	 * @param t
	 *            复制到的新文件
	 * @throws IOException
	 */
	public void copyFile(File s, File t) throws IOException {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(s);
			fo = new FileOutputStream(t);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e) {
				throw e;
			}
		}
	}
}
