package com.byond.savethehumans.learner;

import com.byond.savethehumans.util.FileUtil;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 Here we use Word2Vec as the core algorithm for finding a few number of neighbors. Word2vec is a two-layer neural net that processes text.
 Its input is a text corpus and its output is a set of vectors:  feature vectors for words in that corpus.
 While Word2vec is not a deep neural network, it turns text into a numerical form that deep nets can understand.
 You can find how amazing is here: https://deeplearning4j.org/docs/latest/deeplearning4j-nlp-word2vec
 */

@ComponentScan
@Component
public class WordToVector {
    private static Logger log = LoggerFactory.getLogger(WordToVector.class);

    @Value("${dictionary}")
    private String dictionary;

    @Value("${basePath}")
    private String basePath;

    @Value("${deeplearningResourcesRootPath}")
    private String deeplearningResourcesRootPath;


    @Value("${wordToVector.enabled}")
    private Boolean word2VecIsEnabled;

    private boolean inProcess = false;

    public boolean isInProcess() {
        return inProcess;
    }

    /* keeping the tranined model alive in memory. */
    private Word2Vec word2Vec;

    /* After construct it starts to learn.*/
    @PostConstruct
    private void init(){
        if (word2VecIsEnabled) {
            this.inProcess = true;
            try {
                this.computeModel(dictionary);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                this.inProcess = false;
            }
        }
    }

    private void computeModel(String dictionary) throws Exception {


        String filePath = basePath + deeplearningResourcesRootPath + "/dictionary/" + dictionary;

        if (!FileUtil.fileExists(filePath)){
            log.error("File not found: {}", filePath);
            throw new FileNotFoundException();
        }

        log.info("Load & Vectorize Sentences....");
        // Strip white space before and after for each line
        SentenceIterator iter = new BasicLineIterator(filePath);
        // Split on white spaces in the line to get words
        TokenizerFactory t = new DefaultTokenizerFactory();

        /*
            CommonPreprocessor will apply the following regex to each token: [\d\.:,"'\(\)\[\]|/?!;]+
            So, effectively all numbers, punctuation symbols and some special symbols are stripped off.
            Additionally it forces lower case for all tokens.
         */
        t.setTokenPreProcessor(new CommonPreprocessor());

        log.info("Building model....");
        this.word2Vec = new Word2Vec.Builder()
                .minWordFrequency(5)
                .iterations(1)
                .layerSize(100)
                .seed(42)
                .windowSize(5)
                .iterate(iter)
                .tokenizerFactory(t)
                .build();

        log.info("Fitting Word2Vec model....");
        this.word2Vec.fit();

        log.info("Writing word vectors to text file....");


        log.info("Closest Words:");
        Collection<String> lst = this.word2Vec.wordsNearestSum("amazon", 10);
        log.info("10 Words closest to 'amazon': {}", lst);

        log.info("Closest Words:");
        Collection<String> lst2 = this.word2Vec.wordsNearestSum("amazing", 10);
        log.info("10 Words closest to 'amazing': {}", lst2);

    }

    public Set<String> getNearWords(String word, Integer count){
        Collection<String> lst = null;
        try {
            lst = this.word2Vec.wordsNearestSum(word, count);
        } catch (Exception ex) {
            if (lst==null){
                return new HashSet<String>();
            }
        }

        Set result = new HashSet();
        for(String str : lst){
            result.add(str.toLowerCase().trim());
        }
        return result;
    }

}