package com.lucenetutorial.lucene;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class DeleteDocuments {
	IndexWriter writer;
	
	public void deleteSpecificDocs(ArrayList<String> documents) throws IOException {
		Path path = Paths.get(LuceneConstants.INDEX_DIR);
     	Directory directory = FSDirectory.open(path);
     	IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
		writer = new IndexWriter(directory,config);
		for(String article : documents){
			writer.deleteDocuments(new Term(LuceneConstants.FILE_NAME,article));
			writer.commit();
		}
		
		System.out.println("Index contains deleted files: "+writer.hasDeletions());
	    System.out.println("Index contains documents: ");
	    writer.close();
	}

	public boolean checkIfDocumentExits() {
		return true;
	}
	
}
