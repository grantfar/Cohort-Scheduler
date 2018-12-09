import * as mongoose from 'mongoose';

const scheduleSchema = new mongoose.Schema({
  name: String,
  date: Date,
});

const Schedule = mongoose.model('Schedule', scheduleSchema);

export default Schedule;
