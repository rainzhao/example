package com.example.demo.qleexpress;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: zhaoyu
 * @date: 2021/6/21
 * @description:
 */
@Getter
@Setter
public class TestMyBuider {

    private String userName;

    private String paasword;

    private TestMyBuider(Builder builder) {
        setUserName(builder.userName);
        setPaasword(builder.paasword);
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    /**
     * {@code TestMyBuider} builder static inner class.
     */
    public static final class Builder {
        private String userName;
        private String paasword;

        private Builder() {
        }

        /**
         * Sets the {@code userName} and returns a reference to this Builder so that the methods can be chained
         * together.
         *
         * @param val the {@code userName} to set
         * @return a reference to this Builder
         */
        public Builder withUserName(String val) {
            userName = val;
            return this;
        }

        /**
         * Sets the {@code paasword} and returns a reference to this Builder so that the methods can be chained
         * together.
         *
         * @param val the {@code paasword} to set
         * @return a reference to this Builder
         */
        public Builder withPaasword(String val) {
            paasword = val;
            return this;
        }

        /**
         * Returns a {@code TestMyBuider} built from the parameters previously set.
         *
         * @return a {@code TestMyBuider} built with parameters of this {@code TestMyBuider.Builder}
         */
        public TestMyBuider build() {
            return new TestMyBuider(this);
        }
    }


}
