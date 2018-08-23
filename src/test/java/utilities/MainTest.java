package utilities;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import java.sql.SQLException;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.poi.util.SystemOutLogger;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.jayway.jsonpath.JsonPath;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class MainTest {
	public static void main(String[] args) throws Exception {
		HashMap<String, String> customerExcel = Excel
				.readXSLXFileID("test-data/GuruBank.xlsx", "NewCustomer", "NewCustomer01").get(0);
		
		List<HashMap<String, String>> depositsExcel = Excel
				.readXSLXFileID("test-data/GuruBank.xlsx", "Deposit", "Deposit01");
		
		System.out.println(customerExcel);
		System.out.println(depositsExcel);
	}
}
