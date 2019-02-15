public enum Player {
	CROSS(Flag.CROSS), CIRCLE(Flag.CIRCLE);

	private Flag f;

	private Player(Flag f) {
		this.f = f;
	}

	public Flag toFlag() {
		return this.f;
	}
}