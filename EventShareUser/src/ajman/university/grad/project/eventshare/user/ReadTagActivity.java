package ajman.university.grad.project.eventshare.user;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import ajman.university.grad.project.eventshare.common.contracts.IErrorService;
import ajman.university.grad.project.eventshare.common.contracts.ILocalStorageService;
import ajman.university.grad.project.eventshare.common.models.Event;
import ajman.university.grad.project.eventshare.common.services.ServicesFactory;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class ReadTagActivity extends Activity {

	static String separator = System.getProperty("line.separator");
	public static String calString;

	private TextView tvReadTag;
	private NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;
	private IntentFilter[] mIntentFilters;
	private String[][] mNFCTechLists;
	private static boolean read = false;

	public static final String MIME_TEXT_CALENDAR = "text/x-vcalendar";
	private static final String HEXES = "0123456789ABCDEF";

	private static final byte[] KEYA = {
			(byte) 0xd3, (byte) 0xf7, (byte) 0xd3,
			(byte) 0xf7, (byte) 0xd3, (byte) 0xf7
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_tag);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		tvReadTag = (TextView) findViewById(R.id.tv_read_tag);

		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

		// create an intent with tag data and deliver to this activity
		mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

		// set an intent filter for all MIME data
		IntentFilter ndefIntent = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
		try {
			ndefIntent.addDataType("*/*");
			mIntentFilters = new IntentFilter[] { ndefIntent };
		} catch (Exception e) {
			Log.e("TagDispatch", e.toString());
		}

		mNFCTechLists = new String[][] { new String[] { MifareClassic.class.getName() } };
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onResume() {
		super.onResume();

		// Ensure that the device supports NFC
		ensureNfcIsAvailable();

		if (mNfcAdapter != null) {
			// Ensure that the device's NFC sensor is on
			ensureSensorIsOn();

			mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mIntentFilters, mNFCTechLists);
		}
	}

	@Override
	public void onNewIntent(Intent intent) {
		Log.i("Foreground dispatch", "Discovered tag with intent: " + intent);
		if (!read) {
			processIntent(intent);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		mNfcAdapter.disableForegroundDispatch(this);
	}

	private void processIntent(Intent intent) {
		Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

		boolean auth = false;
		MifareClassic mfc = MifareClassic.get(tagFromIntent);

		try {
			String metaInfo = "";
			mfc.connect();

			outer: for (int j = 0; j < mfc.getSectorCount(); j++) {

				// Authenticate a sector with key.
				auth = mfc.authenticateSectorWithKeyA(j, KEYA);
				int bCount;
				int bIndex;
				if (auth) {
					System.out.println("Sector " + j + " authenticated successfully");
					bCount = mfc.getBlockCountInSector(j);
					bIndex = mfc.sectorToBlock(j);

					for (int i = 0; i < bCount; i++) {

						try {
							byte[] data = mfc.readBlock(bIndex);
							if (byteArrayToHexString(data).equals("00000000000000000000000000000000")) {
								System.out.println("stopped reading");
								break outer;
							}

						} catch (Exception e) {
							System.out.println("Read error at: " + bIndex);
						}
						
						// Read data blocks with key A
						if ((bIndex + 1) % bCount != 0 && bIndex != 0) {
							try {
								byte[] data = mfc.readBlock(bIndex);
								metaInfo += byteArrayToHexString(data);
							} catch (Exception e) {
								System.out.println("Cound not read block nr: "
										+ bIndex);
							}
						}

						bIndex++;
					}
				} else {
					System.out.println("Sector " + j + " could not be authenticated");
				}
			}
			tvReadTag.setText(hexToASCII(metaInfo));

			mfc.close();
			read = true;

			Event event;
			ILocalStorageService service = ServicesFactory.getLocalStorageService();
			IErrorService errorService = ServicesFactory.getErrorService();

			try {

				String xmlCalendar = hexToASCII(metaInfo);
				System.out.println(xmlCalendar);
				DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(xmlCalendar));
				Document doc = db.parse(is);

				NodeList nodes = doc.getElementsByTagName("event");

				service.deleteAllEvents();
				
				for (int i = 0; i < nodes.getLength(); i++) {
					event = new Event();

					Element attr = (Element) ((Element) nodes.item(i)).getElementsByTagName("summary").item(0);
					event.setTitle(getCharacterDataFromElement(attr));
					System.out.println("Summary: " + getCharacterDataFromElement(attr));

					attr = (Element) ((Element) nodes.item(i)).getElementsByTagName("description").item(0);
					event.setDescription(getCharacterDataFromElement(attr));
					System.out.println("Description: " + getCharacterDataFromElement(attr));

					attr = (Element) ((Element) nodes.item(i)).getElementsByTagName("location").item(0);
					event.setLocation(getCharacterDataFromElement(attr));
					System.out.println("Location: " + getCharacterDataFromElement(attr));

					attr = (Element) ((Element) nodes.item(i)).getElementsByTagName("uid").item(0);
					event.setId(Integer.parseInt(getCharacterDataFromElement(attr)));
					System.out.println("Uid: " + getCharacterDataFromElement(attr));

					attr = (Element) ((Element) nodes.item(i)).getElementsByTagName("dtstart").item(0);
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
					cal.setTime(sdf.parse(getCharacterDataFromElement(attr)));
					
					event.setFromYear(cal.get(Calendar.YEAR));
					System.out.println("Fromyear: " + cal.get(Calendar.YEAR));

					event.setFromMonth(cal.get(Calendar.MONTH));
					System.out.println("FromMonth: " + cal.get(Calendar.MONTH));

					event.setFromDay(cal.get(Calendar.DAY_OF_MONTH));
					System.out.println("FromDay: " + cal.get(Calendar.DAY_OF_MONTH));

					event.setFromDayHour(cal.get(Calendar.HOUR_OF_DAY));
					System.out.println("FromHour: " + cal.get(Calendar.HOUR_OF_DAY));

					event.setFromDayHour(cal.get(Calendar.MINUTE));
					System.out.println("Fromminute: " + cal.get(Calendar.MINUTE));

					attr = (Element) ((Element) nodes.item(i)).getElementsByTagName("dtend").item(0);
					cal.setTime(sdf.parse(getCharacterDataFromElement(attr)));

<<<<<<< HEAD
					event.setToYear(cal.get(Calendar.YEAR));
					System.out.println("Toyear: " + cal.get(Calendar.YEAR));

					event.setToMonth(cal.get(Calendar.MONTH));
					System.out.println("ToMonth: " + cal.get(Calendar.MONTH));

					event.setToDay(cal.get(Calendar.DAY_OF_MONTH));
					System.out.println("ToDay: " + cal.get(Calendar.DAY_OF_MONTH));
=======
//					event.setToYear(cal.YEAR);
//					System.out.println("Toyear: " + cal.YEAR);
//
//					event.setToMonth(cal.MONTH);
//					System.out.println("ToMonth: " + cal.MONTH);
//
//					event.setToDay(cal.DAY_OF_MONTH);
//					System.out.println("ToDay: " + cal.DAY_OF_MONTH);
>>>>>>> cdeab9e1c9d40c67b6bc9f976606fe35ac94456d

					event.setToDayHour(cal.get(Calendar.HOUR_OF_DAY));
					System.out.println("ToHour: " + cal.get(Calendar.HOUR_OF_DAY));

					event.setToDayHour(cal.get(Calendar.MINUTE));
					System.out.println("Tominute: " + cal.get(Calendar.MINUTE));

					service.addEvent(event);
				}

			} catch (Exception e) {
				System.out.println("Parse error occured!!");
				errorService.log(e);
			}

			new AlertDialog.Builder(this).setMessage("Tag successfully read!").setCancelable(false)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							read = false;
							finish();

						}
					}).show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}

	public static String byteArrayToHexString(byte[] raw) {
		if (raw == null) {
			return null;
		}
		final StringBuilder hex = new StringBuilder(2 * raw.length);
		for (final byte b : raw) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}

	public static String hexToASCII(String hex) {
		if (hex.length() % 2 != 0) {
			System.err.println("requires EVEN number of chars");
			return null;
		}
		StringBuilder sb = new StringBuilder();
		// Convert Hex 0232343536AB into two characters stream.
		for (int i = 0; i < hex.length() - 1; i += 2) {
			/*
			 * Grab the hex in pairs
			 */
			String output = hex.substring(i, (i + 2));
			/*
			 * Convert Hex to Decimal
			 */
			int decimal = Integer.parseInt(output, 16);
			sb.append((char) decimal);
		}
		return sb.toString();
	}

	private void ensureNfcIsAvailable() {

		if (mNfcAdapter == null) {
			// Stop here, we definitely need NFC
			Toast.makeText(this, "This device doesn't support NFC.",
					Toast.LENGTH_LONG).show();
			finish();
			return;
		}
	}

	private void ensureSensorIsOn() {
		if (mNfcAdapter != null && !mNfcAdapter.isEnabled()) {
			// Alert the user that NFC is off
			new AlertDialog.Builder(this)
					.setTitle("NFC Sensor Turned Off")
					.setMessage(
							"In order to use this application, the NFC sensor must be turned on. Do you wish to turn it on?")
					.setPositiveButton("Go to Settings",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										DialogInterface dialogInterface, int i) {
									// Send the user to the settings page and
									// hope they turn it on
									if (android.os.Build.VERSION.SDK_INT >= 16) {
										startActivity(new Intent(
												android.provider.Settings.ACTION_NFC_SETTINGS));
									} else {
										startActivity(new Intent(
												android.provider.Settings.ACTION_WIRELESS_SETTINGS));
									}
								}
							})
					.setNegativeButton("Do Nothing",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										DialogInterface dialogInterface, int i) {
									// Do nothing
								}
							}).show();
		}
	}

}
