#include <WiFiManager.h>
#include <Firebase_ESP_Client.h>
#include <addons/TokenHelper.h>
#include <DHT.h>  // Include the DHT sensor library
#include <time.h> // Thêm thư viện time
#include <Wire.h>                 // Thư viện giao tiếp I2C
#include <LiquidCrystal_I2C.h>    // Thư viện LCD
LiquidCrystal_I2C lcd(0x27,16,2); // Thiết lập địa chỉ và loại LCD

// Define DHT sensor parameters
#define DHTPIN 12
#define GASPIN A0
#define DHTTYPE DHT11

// Define WiFi credentials
#define WIFI_SSID "Bo Be"
#define WIFI_PASSWORD "01234567890"

// Define Firebase API Key, Project ID, and user credentials
#define API_KEY "AIzaSyDAkBPEYmLFmvBSIaVDSOEPrAk3ueXlBHs"
#define FIREBASE_PROJECT_ID "urban-area-manager"
#define USER_EMAIL "iot@gmail.com"
#define USER_PASSWORD "123456789"

// Define serial number for the device
#define DEVICE_SERIAL "device_001"

FirebaseData fbdo;
FirebaseAuth auth;
FirebaseJson content;
FirebaseConfig config;
unsigned long lastDocumentTime = 0; // thời gian tạo tài liệu cuối cùng
const unsigned long documentInterval = 900000; // 15 phút (15*60*1000)

// Initialize the DHT sensor
DHT dht(DHTPIN, DHTTYPE);

void configModeCallback (WiFiManager *myWiFiManager) {
  Serial.println("Entered config mode");
  Serial.println(WiFi.softAPIP());
  //if you used auto generated SSID, print it
  Serial.println(myWiFiManager->getConfigPortalSSID());
}
void setup() {
  Serial.begin(115200);
  dht.begin();
  WiFiManager wifiManager;
  wifiManager.setAPCallback(configModeCallback);
  Serial.print("Connecting to Wi-Fi");
  wifiManager.setAPCallback(configModeCallback);
  Wire.begin(D4, D3);
  lcd.init();                      // Khởi tạo LCD
  lcd.clear();                     // Xóa màn hình
  lcd.backlight();   
    if (WiFi.status() != WL_CONNECTED) {

    lcd.clear(); 
    lcd.setCursor(0, 0);
    lcd.print("WIFI NOT CONNECT");
    lcd.setCursor(0, 1);
    lcd.print("LOGIN WIFI IOT12");
    Serial.print(".");
    delay(1500);
    // yield(); // Add this line
  }             // bật đèn nền
  if(!wifiManager.autoConnect("IOT12",DEVICE_SERIAL)) {
    Serial.println("failed to connect and hit timeout");
    //reset and try again, or maybe put it to deep sleep
    ESP.restart();
    delay(1500);
  } 

  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();

  // Print Firebase client version
  Serial.printf("Firebase Client v%s\n\n", FIREBASE_CLIENT_VERSION);

  // Assign the API key
  config.api_key = API_KEY;

  // Assign the user sign-in credentials
  auth.user.email = USER_EMAIL;
  auth.user.password = USER_PASSWORD;

  // Assign the callback function for the long-running token generation task
  config.token_status_callback = tokenStatusCallback;  // see addons/TokenHelper.h

  // Begin Firebase with configuration and authentication
  Firebase.begin(&config, &auth);

  // Reconnect to Wi-Fi if necessary
  Firebase.reconnectWiFi(true);

  // Đặt múi giờ, ví dụ GMT+7
  configTime(25200, 0, "pool.ntp.org", "time.nist.gov");
}

String createDocumentPath() {
  time_t now = time(nullptr);
  struct tm * timeinfo = localtime(&now);
  char buffer[30];
  strftime(buffer, sizeof(buffer), "%Y%m%d_%H%M%S", timeinfo);
  String timestamp(buffer);
  return "SensorHistoric/" + String(DEVICE_SERIAL) + "/" + String(DEVICE_SERIAL) + "/" + timestamp;
}

void loop() {
  // Define the path to the Firestore document
  String documentPathRealtime = "SensorRealtime/" + String(DEVICE_SERIAL);

  // Lấy thời gian hiện tại
  time_t now = time(nullptr);
  struct tm * timeinfo = gmtime(&now);

  FirebaseJson timestamp;
  timestamp.set("seconds", (int)now);
  timestamp.set("nanos", 0);

  // Read temperature and humidity from the DHT sensor
  float temperature = dht.readTemperature();
  float humidity = dht.readHumidity();
  int gasValue = analogRead(GASPIN);

  // Print temperature and humidity values
  Serial.println(temperature);
  Serial.println(humidity);
  Serial.println(gasValue);

  // Hiển thị nhiệt độ và độ ẩm lên màn hình LCD
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("TEMP: " + String(temperature) +"C");
  lcd.setCursor(0, 1);
  lcd.print("HUMID: " + String(humidity) + "%");
  delay(1500);
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("AIR: " + String(gasValue) + "PPM");
  if(WiFi.status() == WL_CONNECTED){
      lcd.setCursor(0, 1);
      lcd.print("DESIGN BY DUC");
  }
  else{
      lcd.setCursor(0, 1);
      lcd.print("WIFI NOT CONNECT");
  }
 



  // Check if the values are valid (not NaN)
  if (!isnan(temperature) && !isnan(humidity)) {
    // Set the 'Temperature' and 'Humidity' fields in the FirebaseJson object
    content.set("fields/temp/doubleValue", temperature);
    content.set("fields/humid/doubleValue", humidity);
    content.set("fields/gas/integerValue", gasValue);
    content.set("fields/time/timestampValue", timestamp);
    content.set("fields/serial/stringValue", DEVICE_SERIAL);

    Serial.print("Update/Add DHT Data... ");

    // Use the patchDocument method to update the Temperature and Humidity Firestore document
    if (Firebase.Firestore.patchDocument(&fbdo, FIREBASE_PROJECT_ID, "", documentPathRealtime.c_str(), content.raw(), "temp,humid,gas,time,serial")) {
      Serial.printf("ok\n%s\n\n", fbdo.payload().c_str());
    } else {
      Serial.println(fbdo.errorReason());
    }
  } else {
    Serial.println("Failed to read DHT data.");
  }

  // Check if the document interval has passed
  if (millis() - lastDocumentTime >= documentInterval) {
    lastDocumentTime = millis();
    String documentPath = createDocumentPath();

    Serial.print("Update/Add Historical DHT Data... ");

    // Use the patchDocument method to update the historical Firestore document
    if (Firebase.Firestore.patchDocument(&fbdo, FIREBASE_PROJECT_ID, "", documentPath.c_str(), content.raw(), "temp,humid,gas,time,serial")) {
      Serial.printf("ok\n%s\n\n", fbdo.payload().c_str());
    } else {
      Serial.println(fbdo.errorReason());
    }
  }

  // Delay before the next reading
  delay(3000);
}
