var karmaConfig = require('./karma.conf.js');
karmaConfig.autoWatch = false;
karmaConfig.singleRun = true;
module.exports = function (config) {
    karmaConfig.logLevel = config.LOG_INFO;
    config.set(karmaConfig);
};
