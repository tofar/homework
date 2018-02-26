package manila.model.position;

/**
 * 保险公司
 */
public class InsuranceCompany extends Position {
    /**
     * 该位置默认收入
     */
    private int profit;

    /**
     * 保险公司构造函数
     *
     * @param profit 成功之后的利润
     * @param price  位置购买价格
     */
    public InsuranceCompany(int profit, int price) {
        super(price);
        this.profit = profit;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit() {
        this.profit = profit;
    }

    /**
     * 支付沉船费用
     *
     * @param pay_price 沉船停靠港口价格
     */
    public void pay(int pay_price) {
        if (this.getPlayer() != null) {
            this.getPlayer().cutAccount_balance(pay_price);
        }

    }
}
