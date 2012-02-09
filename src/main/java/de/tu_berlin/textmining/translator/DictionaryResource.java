package de.tu_berlin.textmining.translator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.sun.jersey.spi.resource.Singleton;

import de.tu_berlin.textmining.translator.prototypes.Translator;
import de.tu_berlin.textmining.translator.prototypes.data.lexicon.Dictionary;
import de.tu_berlin.textmining.translator.prototypes.data.lexicon.HashDictionary;
import de.tu_berlin.textmining.translator.prototypes.models.BiGramModel;
import de.tu_berlin.textmining.translator.prototypes.models.LanguageModel;
import de.tu_berlin.textmining.translator.prototypes.reader.ElcombriReader;

@Path("translator")
@Singleton
public class DictionaryResource {
	
	private static final String PATH_TO_DICT = "/Users/carabolic/Abgelegt/dictionaries/dict_cc/de-en_elcombri.txt";
	private static final String PATH_TO_CORPUS = "/Users/carabolic/Development/repos/translator-prototypes/src/main/resources/prototypes/Corpus.txt";
	private static final Logger LOGGER = Logger.getLogger(DictionaryResource.class);
	//private Dictionary dict;
	private Translator translator;
	
	public DictionaryResource() {
		BasicConfigurator.configure();
		LOGGER.setLevel(Level.ALL);
		try {
			LOGGER.info("Load dictionary from file: " + PATH_TO_DICT);
			Dictionary dict = new HashDictionary(new ElcombriReader(PATH_TO_DICT));
			LOGGER.info("Train language model from file: " + PATH_TO_CORPUS);
			LanguageModel langModel = new BiGramModel(new BufferedReader(new FileReader(PATH_TO_CORPUS)));
			this.translator = new Translator(dict, langModel);
		} catch (FileNotFoundException e) {
			LOGGER.error("Could not find dictionary file at: " + PATH_TO_DICT, e);
			System.exit(1);
		} catch (IOException e) {
			LOGGER.fatal("An error occured while opening " + PATH_TO_DICT, e);
			System.exit(1);
		}
	}
	
	@Path("translate")
	@GET
	@Produces("text/plain")
	public String translate(@DefaultValue("") @QueryParam("q") final String sentence) {
		LOGGER.debug("Got query q=" + sentence);
		return this.translator.translateSentence(sentence);
	}
}
