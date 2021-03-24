const patterns = () => {
    const packages = ["firebase-storage-lite"];
    const base = "/node_modules/(?!{PACKAGE})";
    const ignorePatterns = [];
    for (const _package of packages) {
        const pattern = base.replace("{PACKAGE}", _package);
        ignorePatterns.push(pattern);
    }
    return ignorePatterns;
};

module.exports = {
    maxConcurrency: 1,
    verbose: true,
    transform: {
        ".*\\.(vue)$": "vue-jest",
        ".*\\.(js)$": "babel-jest",
    },
    transformIgnorePatterns: patterns(),
    collectCoverageFrom: ["src/**/*.js", "src/**/*.vue"],
    setupFilesAfterEnv: ["<rootDir>/tests/unit/setup.js"],
    slowTestThreshold: 15,
};
