package com.lyk.readwar;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;


/**
 * @author lyk
 *
 */
public class ReadWar {

	public static void main(String[] args) {
		try {
			File outFile = new File("D:/myspring1.war");
			outFile.createNewFile();
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outFile));
			ArchiveOutputStream out = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.JAR,
					bufferedOutputStream);
			
			ZipFile zipFile = new ZipFile("D:/myspring.war");
			Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
			// 遍历条目。
			while (entries.hasMoreElements()) {
				ZipArchiveEntry jarEntry = entries.nextElement();
				System.out.println(jarEntry);
				InputStream in = zipFile.getInputStream(jarEntry);
				out.putArchiveEntry(jarEntry);
				if (jarEntry.getName().endsWith(".properties")) {
					Properties properties = new Properties();
					properties.load(in);
					if (properties.getProperty("jdbc.url") != null) {
						properties.setProperty("jdbc.url", "4654545");
						properties.store(out, "sdf");
					}
				}
				IOUtils.copy(in, out);
				out.closeArchiveEntry();
			}
			out.finish();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArchiveException e) {
			e.printStackTrace();
		}

	}
}
