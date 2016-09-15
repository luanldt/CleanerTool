package value;

public enum CustomExtension {
	JPEG(".jpg"), MP3(".mp3"), MP4(".mp4"), PDF(".pdf"), PNG(".png"), GIF(".gif"),
	FLV(".flv"), MOV(".mov"), FLAC(".flac"), M4A(".m4a"), TXT(".txt"), DOC(".doc"), DOCX(".docx");

	private String type;

	CustomExtension(String type) {
		this.type = type;
	}

	public String getInstance() {
		return type;
	}
}
