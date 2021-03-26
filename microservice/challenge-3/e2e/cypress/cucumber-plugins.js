const cucumber = require("cypress-cucumber-preprocessor").default;
module.exports = (on, config) => {
    //on is used to hook into various events Cypress emits
    //config is the resolved Cypress config
    on("file:preprocessor", cucumber());
    return Object.assign({}, config, {
        fixturesFolder: "fixtures",
        integrationFolder: "tests"
    });
};
