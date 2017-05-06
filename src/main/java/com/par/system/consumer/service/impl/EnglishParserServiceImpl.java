package com.par.system.consumer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.par.system.beans.EngilshTagResponse;
import com.par.system.consumer.service.EnglishParserService;


public class EnglishParserServiceImpl implements EnglishParserService {

	private static Logger log = LoggerFactory.getLogger(EnglishParserServiceImpl.class);
	
//	@Autowired
//	private TrainnedModelParser trainnedModelParser;

	@Override
	public EngilshTagResponse getParsedSentence(String sentence) {
		
		EngilshTagResponse response = new EngilshTagResponse();
		
//		try {
//			response.setInputSentence(sentence);
//			if(grammarCheck){
//				EnglishGrammarCheckResponse checkSentence = englishGrammarCheck.checkGrammar(sentence);
//				if(checkSentence.getHttpStatus() == HttpStatus.OK){
//					sentence = checkSentence.getSentence();
//					response.setGrammarCorrected(sentence);
//					response.setEnglishGrammarCheckResponse(checkSentence);
//				}else{
//					response.setEnglishGrammarCheckResponse(new EnglishGrammarCheckResponse());
//					response.setGrammarCorrected("Failed to parse: "+ checkSentence.getErrorObject().getMessage());
//				}
//			}else{
//				response.setEnglishGrammarCheckResponse(new EnglishGrammarCheckResponse());
//				response.setGrammarCorrected("Grammar Check = FALSE");
//			}
//			
//			Tree tree = trainnedModelParser.getLexicalizedParserTree(sentence);
//			if(tree != null){
//				List<WordTagging> tagWords = new ArrayList<>();
//				List<Tree> leaves = tree.getLeaves();
//				for (Tree leaf : leaves) {
//					Tree parent = leaf.parent(tree);
//					tagWords.add(new WordTagging(leaf.label().value(), parent.label().value()));
//				}
//				response.setHttpStatus(HttpStatus.OK);
//				response.setTagWords(tagWords);
//			}else{
//				throw new Exception("Parsed Tree is Empty/Null.");
//			}
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			ErrorObject errorObject = new ErrorObject(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage());
//			response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//			response.setErrorObject(errorObject);
//		}
		return response;
	}
}
