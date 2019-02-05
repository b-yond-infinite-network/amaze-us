package com.challenge.suitecrm.ingester;

public interface SystemExiter {

    void exit(int code);


    class Default implements SystemExiter{

        @Override
        public void exit(int code) {
            System.exit(code);
        }
    }

    class Testing implements SystemExiter{

        @Override
        public void exit(int code) {
            if(0 != code){
                throw new RuntimeException("System exits with code : " + code);
            }
        }
    }

}
