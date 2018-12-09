"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var mongoose = require("mongoose");
var scheduleSchema = new mongoose.Schema({
    name: String,
    date: Date,
});
var Schedule = mongoose.model('Schedule', scheduleSchema);
exports.default = Schedule;
//# sourceMappingURL=schedule.js.map