import * as mongoose from 'mongoose';

const cohortSchema = new mongoose.Schema({
  cohort: String,
  class: String,
  required: Number,
  sections: String
});

const Cohort = mongoose.model('Cohort', cohortSchema);

export default Cohort;
