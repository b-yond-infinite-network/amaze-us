package com.challenge.suitecrm.ingester.commons;

import fj.data.Either;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecord;
import org.beanio.BeanReader;
import org.beanio.BeanReaderException;
import org.beanio.RecordContext;
import org.beanio.StreamFactory;
import org.beanio.builder.StreamBuilder;
import org.beanio.types.StringTypeHandler;

public abstract class DatasetParser<K extends org.apache.avro.specific.SpecificRecord, B extends org.apache.avro.specific.SpecificRecord>  {

    private StreamBuilder streamBuilder;

    public final String streamName;

    public final Schema recordAvroSchema;

    private final StreamFactory streamFactory;

    protected DatasetParser(final String streamName) {
        this.streamBuilder = createStreamBuilder(getType());
        this.streamName = streamName;
        this.streamFactory = newStreamFactory(streamBuilder);
        this.recordAvroSchema = extractAvroSchema();
    }

    private StreamFactory newStreamFactory(StreamBuilder streamBuilder) {
        StreamFactory streamFactory = StreamFactory.newInstance();
        StringTypeHandler nullableStringTypeHandler = new StringTypeHandler();
        nullableStringTypeHandler.setNullIfEmpty(true);
        nullableStringTypeHandler.setTrim(true);
        streamBuilder.addTypeHandler(String.class,nullableStringTypeHandler);
        streamFactory.define(streamBuilder);
        return streamFactory;
    }

    public Stream<Either<RecordError,B>> parse(final InputStream inputStream){
        return parse(null, inputStream);
    }

    public Stream<Either<RecordError,B>> parse(final String fileName, final InputStream inputStream){
        final DatasetIterator<B> iterator = new DatasetIterator<>(fileName, streamFactory, streamName, inputStream);
        final Stream<Either<RecordError, B>> stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED | Spliterator.IMMUTABLE | Spliterator.NONNULL), false);
        return stream.onClose(() -> {
            try{iterator.close();inputStream.close();}
            catch (IOException e){ /* silent close intentionally do nothing*/}
        });
    }

    public K getKey(SpecificRecord record) {
        return getKeyFromSpecificType(getType().cast(record));
    }

    protected abstract StreamBuilder createStreamBuilder(Class<B> recordType);

    protected abstract K getKeyFromSpecificType(B record);

    private Schema extractAvroSchema(){
        final Class<B> bClass = getType();
        try {
            return (Schema) bClass.getDeclaredMethod("getClassSchema").invoke(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Class<B> getType() {
        return (Class<B>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }


    public  static class DatasetIterator<B> implements Iterator<Either<RecordError,B>>, Closeable {

        private final BeanReader beanReader;
        private Either<RecordError,B> container;
        private final String streamName;
        private final String fileName;

        public  DatasetIterator(String fileName, StreamFactory streamFactory, String streamName, InputStream inputStream) {
            this.streamName = streamName;
            this.fileName = fileName;
            this.beanReader = streamFactory.createReader(streamName, new InputStreamReader(inputStream));
            this.container = readOccurence();
        }


        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasNext() {
            return (container != null);
        }


        /**
         * {@inheritDoc}
         */
        @Override
        public Either<RecordError, B> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            final Either<RecordError, B> response = container;
            container = readOccurence();
            return response;
        }


        /**
         * {@inheritDoc}
         */
        @Override
        public void close() throws IOException {
            beanReader.close();
        }

        private final Either<RecordError,B> readOccurence(){
            try{
              B occurence = (B)this.beanReader.read();
              return occurence==null?null:Either.right(occurence);
            }catch (BeanReaderException e){
                final RecordContext recordContext = e.getRecordContext();
                return Either.left(new RecordError(streamName, fileName, recordContext.getLineNumber(),recordContext.getRecordText(),recordContext.getFieldErrors()));
            }
        }
    }
    public static class RecordError {

        final String streamName;
        final String fileName;
        final int lineNumber;
        final String lineContent;

        public final Map<String, Collection<String>> fieldErrors;

        public RecordError(String streamName, String fileName, int lineNumber, String lineContent, Map<String, Collection<String>> fieldErrors) {
            this.streamName = streamName;
            this.fileName = fileName;
            this.lineNumber = lineNumber;
            this.lineContent = lineContent;
            this.fieldErrors = fieldErrors;
        }

        @Override public String toString() {
            return "RecordError{" + "streamName='" + streamName + '\'' + ", fileName='" + fileName + '\'' + ", lineNumber=" + lineNumber
                + ", lineContent='" + lineContent + '\'' + ", fieldErrors=" + fieldErrors + '}';
        }

        @Override public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            RecordError that = (RecordError) o;

            if (lineNumber != that.lineNumber)
                return false;
            if (streamName != null ? !streamName.equals(that.streamName) : that.streamName != null)
                return false;
            if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null)
                return false;
            if (lineContent != null ? !lineContent.equals(that.lineContent) : that.lineContent != null)
                return false;
            return fieldErrors != null ? fieldErrors.equals(that.fieldErrors) : that.fieldErrors == null;
        }

        @Override public int hashCode() {
            int result = streamName != null ? streamName.hashCode() : 0;
            result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
            result = 31 * result + lineNumber;
            result = 31 * result + (lineContent != null ? lineContent.hashCode() : 0);
            result = 31 * result + (fieldErrors != null ? fieldErrors.hashCode() : 0);
            return result;
        }
    }
}
