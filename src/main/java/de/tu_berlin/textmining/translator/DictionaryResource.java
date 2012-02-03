package de.tu_berlin.textmining.translator;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.sun.jersey.spi.resource.Singleton;

import de.tu_berlin.textmining.translator.prototypes.Dictionary;
import de.tu_berlin.textmining.translator.prototypes.HashDictionary;

@Path("translator")
@Singleton
public class DictionaryResource {
	
	private static final String PATH_TO_DICT = "/Users/carabolic/Abgelegt/dictionaries/dict_cc/de-en_elcombri.txt";
	private Dictionary dict;
	
	public DictionaryResource() throws FileNotFoundException, IOException {
		System.out.println("Loading dictionary at: " + PATH_TO_DICT);
		this.dict = new HashDictionary(PATH_TO_DICT);
		System.out.println("DONE!");
	}
	
	@Path("translate")
	@GET
	@Produces("text/plain")
	public String translate(@DefaultValue("") @QueryParam("q") final String sentence) {
		System.out.println("Got: " + sentence);
		return this.dict.translateSentence(sentence);
	}
}
