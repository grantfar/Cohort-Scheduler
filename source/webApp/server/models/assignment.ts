import * as mongoose from 'mongoose';

const assignmentSchema = new mongoose.Schema({
  schedule:String,
  cohort: String,
  class: String,
  sect: String,
  startTime: String,
  endTime: String,
  days: String,
  seats: Number
});

const Assignment = mongoose.model('Assignment', assignmentSchema);

export default Assignment;
