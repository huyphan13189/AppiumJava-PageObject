package utilities;

import java.text.DecimalFormat;

public class StringFormat {
	//ex: formatStringToMoney("659500000000","####,###,###","€ ", "")
	public static String formatStringToMoney(String money,String moneyFormat,String beginText, String afterText) {
		DecimalFormat dFormat = new DecimalFormat(moneyFormat);
		return beginText + dFormat.format(Double.parseDouble(money)) + afterText;
	}
}
