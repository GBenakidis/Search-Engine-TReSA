package com.lucenetutorial.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreProcessoring{
	
	protected String preProcessing(String preProcessedContent) {
		// Removing <Places>, <People>, <Title> and <Body>
		preProcessedContent = removingHTML(preProcessedContent);
		
		// Remove punctuation
		preProcessedContent = removingPunctuation(preProcessedContent);
		
		// Everything to lower case
		preProcessedContent = preProcessedContent.toLowerCase();
		
		// Removing numbers
		preProcessedContent = preProcessedContent.replaceAll("[0-9]","");
		
		// Removing stop words
		preProcessedContent = removingStopwords(preProcessedContent);
		
		return preProcessedContent;
	}

	private String removingStopwords(String preProcessedContent) {
	    Pattern p = Pattern.compile("\\b(I|this|its|i|ii|iii|vi|iv|x|v|y.....)\\b\\s?");
	    Matcher m = p.matcher(preProcessedContent);
	    preProcessedContent = m.replaceAll("");
	    
		return preProcessedContent;
	}
	
	private String removingPunctuation(String preProcessedContent) {
		preProcessedContent = preProcessedContent.replaceAll("\\p{Punct}", "");
		preProcessedContent = preProcessedContent.replaceAll("", "");
		
		return preProcessedContent;
	}
	
	private String removingHTML(String preProcessedContent) {
		preProcessedContent=preProcessedContent.replace("<PLACES>","");
		preProcessedContent=preProcessedContent.replace("</PLACES>","");
		
		preProcessedContent=preProcessedContent.replace("<PEOPLE>","");
		preProcessedContent=preProcessedContent.replace("</PEOPLE>","");
		
		preProcessedContent=preProcessedContent.replace("<TITLE>","");
		preProcessedContent=preProcessedContent.replace("</TITLE>","");
		
		preProcessedContent=preProcessedContent.replace("<BODY>","");
		preProcessedContent=preProcessedContent.replace("</BODY>","");
		
		preProcessedContent=preProcessedContent.replace("    ",""); // useless in general, keep it for System.out
		
		return preProcessedContent;
	}
	
	protected String contentTaker(File file) throws IOException {
		// https://www.journaldev.com/875/java-read-file-to-string
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		String ls = System.getProperty("line.separator");
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}
		// delete the last new line separator
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		reader.close();
		
		return stringBuilder.toString();
	}
	
}
