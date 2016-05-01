package forms;

public class SettingForm {

	public SettingForm() {
	}

	public SettingForm(models.Setting announcement) {

		if (announcement == null)
			return;

		this.id = String.format("%03d", announcement.getId());

		this.type = announcement.getType();
		this.value1 = announcement.getValue1();
		this.value2 = announcement.getValue2();
		this.value3 = announcement.getValue3();
		this.value4 = announcement.getValue4();
	}

	public String id;
	public String type;
	public String value1;
	public String value2;
	public String value3;
	public String value4;

}