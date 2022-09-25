package joshuaepstein.template_mod.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import joshuaepstein.template_mod.Main;

import java.io.*;
import java.util.Random;

public abstract class Config {

	protected static final Random rand = new Random();

	private static final Gson GSON = (new GsonBuilder())
			.excludeFieldsWithoutExposeAnnotation()
			.setPrettyPrinting()
			.create();

	protected String root = "config/" + Main.MOD_ID +"/";

	protected String extension = ".json";

	public void generateConfig() {
		reset();
		try {
			writeConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File getConfigFile() {
		return new File(this.root + getName() + this.extension);
	}

	public abstract String getName();

	public <T extends Config> T readConfig() {
		try {
			return (T) GSON.fromJson(new FileReader(getConfigFile()), getClass());
		} catch (FileNotFoundException e) {
			generateConfig();
			return (T) this;
		}
	}

	protected abstract void reset();

	public void writeConfig() throws IOException {
		File dir = new File(this.root);
		if (!dir.exists() && !dir.mkdirs())
			return;
		if (!getConfigFile().exists() && !getConfigFile().createNewFile())
			return;
		FileWriter writer = new FileWriter(getConfigFile());
		GSON.toJson(this, writer);
		writer.flush();
		writer.close();
	}

}
