package com.challenge.suitecrm.ingester.commons;


import org.slf4j.LoggerFactory;

public interface RejectionsHandler {
    void discard(DatasetParser.RecordError error);

   class Logger implements RejectionsHandler{
    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RejectionsHandler.class);
       @Override
        public void discard(DatasetParser.RecordError error) {
         LOGGER.error(error.toString());
        }
    }
}
