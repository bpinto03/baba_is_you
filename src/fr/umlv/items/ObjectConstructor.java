package fr.umlv.items;

import java.util.Objects;

/**
 * ObjectConstructor to transform an element in another element.
 */
public enum ObjectConstructor {
	/**
	* Baba's ObjectConstructor
	*/
	Baba ("Baba", "images/NounsObjects/BABA_0.gif"),

	/**
	* Flag's ObjectConstructor
	*/
	Flag ("Flag", "images/NounsObjects/FLAG_0.gif"),

	/**
	* Lava's ObjectConstructor
	*/
	Lava ("Lava", "images/NounsObjects/LAVA_0.gif"),

	/**
	* Rock's ObjectConstructor
	*/
	Rock ("Rock", "images/NounsObjects/ROCK_0.gif"),

	/**
	* Skull's ObjectConstructor
	*/
	Skull ("Skull", "images/NounsObjects/SKULL_0.gif"),

	/**
	* Wall's ObjectConstructor
	*/
	Wall ("Wall", "images/NounsObjects/WALL_0.gif"),

	/**
	* Water's ObjectConstructor
	*/
	Water ("Water", "images/NounsObjects/WATER_0.gif"),

	/**
	* Karl's ObjectConstructor
	*/
	Karl ("Karl", "images/NounsObjects/KARL_0.gif");

	private final String typeObject;
	private final String imagePath;

	/**
	 * ObjectConstructor's constructor.
	 * @param typeObject
 	*				 Type object.
 	* @param  imagePath
 	*				  Path image object.
	 * */
	private ObjectConstructor(String typeObject, String imagePath) {
		Objects.requireNonNull(typeObject, "typeObject can not be null");
		Objects.requireNonNull(imagePath, "imagePath can not be null");
		this.typeObject = typeObject;
		this.imagePath = imagePath;
	}

	/**
	 * Getter of the typeObject.
	 * @return
	 * 		   Type of object.
	 * */
	public String getTypeObject() { return typeObject; }

	/**
	 * Getter of the imagePath.
	 * @return
	 * 		   Image path of object.
	 * */
	public String getImagePath() { return imagePath; }
}
