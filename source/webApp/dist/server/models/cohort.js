"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var mongoose = require("mongoose");
var cohortSchema = new mongoose.Schema({
    cohort: String,
    class: String,
    required: Number,
    sections: String
});
var Cohort = mongoose.model('Cohort', cohortSchema);
exports.default = Cohort;
//# sourceMappingURL=cohort.js.map