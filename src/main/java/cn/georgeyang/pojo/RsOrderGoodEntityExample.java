package cn.georgeyang.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RsOrderGoodEntityExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RsOrderGoodEntityExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("createTime =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("createTime <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("createTime >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTime >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("createTime <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("createTime <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("createTime in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("createTime not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("createTime between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("createTime not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("updateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("updateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("updateTime =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("updateTime <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("updateTime >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updateTime >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("updateTime <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("updateTime <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("updateTime in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("updateTime not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("updateTime between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("updateTime not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("orderId is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("orderId is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(Long value) {
            addCriterion("orderId =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(Long value) {
            addCriterion("orderId <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(Long value) {
            addCriterion("orderId >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("orderId >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(Long value) {
            addCriterion("orderId <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(Long value) {
            addCriterion("orderId <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<Long> values) {
            addCriterion("orderId in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<Long> values) {
            addCriterion("orderId not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(Long value1, Long value2) {
            addCriterion("orderId between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(Long value1, Long value2) {
            addCriterion("orderId not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andGoodIdIsNull() {
            addCriterion("goodId is null");
            return (Criteria) this;
        }

        public Criteria andGoodIdIsNotNull() {
            addCriterion("goodId is not null");
            return (Criteria) this;
        }

        public Criteria andGoodIdEqualTo(Integer value) {
            addCriterion("goodId =", value, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdNotEqualTo(Integer value) {
            addCriterion("goodId <>", value, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdGreaterThan(Integer value) {
            addCriterion("goodId >", value, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("goodId >=", value, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdLessThan(Integer value) {
            addCriterion("goodId <", value, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdLessThanOrEqualTo(Integer value) {
            addCriterion("goodId <=", value, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdIn(List<Integer> values) {
            addCriterion("goodId in", values, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdNotIn(List<Integer> values) {
            addCriterion("goodId not in", values, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdBetween(Integer value1, Integer value2) {
            addCriterion("goodId between", value1, value2, "goodId");
            return (Criteria) this;
        }

        public Criteria andGoodIdNotBetween(Integer value1, Integer value2) {
            addCriterion("goodId not between", value1, value2, "goodId");
            return (Criteria) this;
        }

        public Criteria andRsNormIdIsNull() {
            addCriterion("rsNormId is null");
            return (Criteria) this;
        }

        public Criteria andRsNormIdIsNotNull() {
            addCriterion("rsNormId is not null");
            return (Criteria) this;
        }

        public Criteria andRsNormIdEqualTo(Integer value) {
            addCriterion("rsNormId =", value, "rsNormId");
            return (Criteria) this;
        }

        public Criteria andRsNormIdNotEqualTo(Integer value) {
            addCriterion("rsNormId <>", value, "rsNormId");
            return (Criteria) this;
        }

        public Criteria andRsNormIdGreaterThan(Integer value) {
            addCriterion("rsNormId >", value, "rsNormId");
            return (Criteria) this;
        }

        public Criteria andRsNormIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("rsNormId >=", value, "rsNormId");
            return (Criteria) this;
        }

        public Criteria andRsNormIdLessThan(Integer value) {
            addCriterion("rsNormId <", value, "rsNormId");
            return (Criteria) this;
        }

        public Criteria andRsNormIdLessThanOrEqualTo(Integer value) {
            addCriterion("rsNormId <=", value, "rsNormId");
            return (Criteria) this;
        }

        public Criteria andRsNormIdIn(List<Integer> values) {
            addCriterion("rsNormId in", values, "rsNormId");
            return (Criteria) this;
        }

        public Criteria andRsNormIdNotIn(List<Integer> values) {
            addCriterion("rsNormId not in", values, "rsNormId");
            return (Criteria) this;
        }

        public Criteria andRsNormIdBetween(Integer value1, Integer value2) {
            addCriterion("rsNormId between", value1, value2, "rsNormId");
            return (Criteria) this;
        }

        public Criteria andRsNormIdNotBetween(Integer value1, Integer value2) {
            addCriterion("rsNormId not between", value1, value2, "rsNormId");
            return (Criteria) this;
        }

        public Criteria andBuySumIsNull() {
            addCriterion("buySum is null");
            return (Criteria) this;
        }

        public Criteria andBuySumIsNotNull() {
            addCriterion("buySum is not null");
            return (Criteria) this;
        }

        public Criteria andBuySumEqualTo(Integer value) {
            addCriterion("buySum =", value, "buySum");
            return (Criteria) this;
        }

        public Criteria andBuySumNotEqualTo(Integer value) {
            addCriterion("buySum <>", value, "buySum");
            return (Criteria) this;
        }

        public Criteria andBuySumGreaterThan(Integer value) {
            addCriterion("buySum >", value, "buySum");
            return (Criteria) this;
        }

        public Criteria andBuySumGreaterThanOrEqualTo(Integer value) {
            addCriterion("buySum >=", value, "buySum");
            return (Criteria) this;
        }

        public Criteria andBuySumLessThan(Integer value) {
            addCriterion("buySum <", value, "buySum");
            return (Criteria) this;
        }

        public Criteria andBuySumLessThanOrEqualTo(Integer value) {
            addCriterion("buySum <=", value, "buySum");
            return (Criteria) this;
        }

        public Criteria andBuySumIn(List<Integer> values) {
            addCriterion("buySum in", values, "buySum");
            return (Criteria) this;
        }

        public Criteria andBuySumNotIn(List<Integer> values) {
            addCriterion("buySum not in", values, "buySum");
            return (Criteria) this;
        }

        public Criteria andBuySumBetween(Integer value1, Integer value2) {
            addCriterion("buySum between", value1, value2, "buySum");
            return (Criteria) this;
        }

        public Criteria andBuySumNotBetween(Integer value1, Integer value2) {
            addCriterion("buySum not between", value1, value2, "buySum");
            return (Criteria) this;
        }

        public Criteria andPayPriceIsNull() {
            addCriterion("payPrice is null");
            return (Criteria) this;
        }

        public Criteria andPayPriceIsNotNull() {
            addCriterion("payPrice is not null");
            return (Criteria) this;
        }

        public Criteria andPayPriceEqualTo(Double value) {
            addCriterion("payPrice =", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceNotEqualTo(Double value) {
            addCriterion("payPrice <>", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceGreaterThan(Double value) {
            addCriterion("payPrice >", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceGreaterThanOrEqualTo(Double value) {
            addCriterion("payPrice >=", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceLessThan(Double value) {
            addCriterion("payPrice <", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceLessThanOrEqualTo(Double value) {
            addCriterion("payPrice <=", value, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceIn(List<Double> values) {
            addCriterion("payPrice in", values, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceNotIn(List<Double> values) {
            addCriterion("payPrice not in", values, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceBetween(Double value1, Double value2) {
            addCriterion("payPrice between", value1, value2, "payPrice");
            return (Criteria) this;
        }

        public Criteria andPayPriceNotBetween(Double value1, Double value2) {
            addCriterion("payPrice not between", value1, value2, "payPrice");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}