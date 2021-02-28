const patterns = () => {
    const packages = ["firebase-storage-lite"];
    const base = "/node_modules/(?!{PACKAGE})";
    const ignorePatterns = [];
    for (const package of packages) {
        const pattern = base.replace("{PACKAGE}", package);
        ignorePatterns.push(pattern);
    }
    return ignorePatterns;
};

module.exports = {
    clearMocks: true,
    transform: {
        ".*\\.(vue)$": "vue-jest",
        ".*\\.(js)$": "babel-jest",
    },
    transformIgnorePatterns: patterns(),
    collectCoverage: true,
    collectCoverageFrom: ["src/**/*.js", "src/**/*.vue"],
};