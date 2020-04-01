public enum logs {
    RUN("run"),
    ERROR("err");

    private final String text;
    logs(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
