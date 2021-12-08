package com.lucenetutorial.lucene;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer extends PreProcessoring{
	private IndexWriter writer;
	
	public Indexer(String indexDirectoryPath) throws IOException  {
		// This directory will contain the indexes
		Path indexPath = Paths.get(indexDirectoryPath);
		if(!Files.exists(indexPath)) {
			 Files.createDirectory(indexPath);
		 }
		Directory indexDirectory = FSDirectory.open(indexPath);
		// Create the indexer
		IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
		writer = new IndexWriter(indexDirectory, config);
	}

	private Document getDocument(File file) throws IOException {
		Document document = new Document();
	
		// Index file name
		Field fileNameField = new Field(LuceneConstants.FILE_NAME, file.getName(), StringField.TYPE_STORED);
		
		// Index file path
		Field filePathField = new Field(LuceneConstants.FILE_PATH, file.getCanonicalPath(), StringField.TYPE_STORED);
		
		String preProcessed = contentTaker(file);
		
		// Pre-processing
		String postProcessing = preProcessing(preProcessed);
		
		// Index file contents
		Field contentField = new Field(LuceneConstants.CONTENTS, postProcessing, TextField.TYPE_STORED);

		document.add(contentField);
		document.add(fileNameField);
		document.add(filePathField);
		
		return document;
	}

	private void indexFile(File file) throws IOException {
		System.out.println("Indexing " + file.getCanonicalPath());
		Document document = getDocument(file);
		writer.addDocument(document);
	}
	
	public int editIndex(String dataDirPath, FileFilter filter, ArrayList<String> articles) throws IOException {
		// Get all files in the data directory
		File[] files = new File(dataDirPath).listFiles();
		for (File file : files) {
			if(!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() && filter.accept(file) ){			
				for(String str:articles) {
					if(file.getName().contains(str)) {				        	
						indexFile(file);
						articles.remove(str);
						break;
			        }
				}
			}
		}
		
		return writer.numRamDocs();
	}

	public int createIndex(String dataDirPath, FileFilter filter, int i) throws IOException {
		// Get all files in the data directory
		File[] files = new File(dataDirPath).listFiles();
		if(i == 1) {
			for (File file : files) {
				if(!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() && filter.accept(file) ){
					indexFile(file);
				}
			}
		}

		return writer.numRamDocs();
	}
	
	public static void recursiveDelete(File file) {
		// https://www.journaldev.com/833/java-delete-directory-folder
		
        //to end the recursive loop
        if (!file.exists())
            return;
        
        //if directory, go inside and call recursively
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                //call recursively
                recursiveDelete(f);
            }
        }
        //call delete to delete files and empty directory
        file.delete();
        System.out.println("Deleted file/folder: "+file.getAbsolutePath());
    }

	public void close() throws CorruptIndexException, IOException {
		writer.close();
	}
	
}
