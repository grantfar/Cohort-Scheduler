"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var mongoose = require("mongoose");
var assignmentSchema = new mongoose.Schema({
    cohort: String,
    class: String,
    sect: String,
    startTime: String,
    endTime: String,
    days: String,
    seats: Number
});
var Assignment = mongoose.model('Assignment', assignmentSchema);
exports.default = Assignment;
//# sourceMappingURL=assignment.js.map