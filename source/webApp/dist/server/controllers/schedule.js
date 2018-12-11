"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = require("tslib");
var schedule_1 = require("../models/schedule");
var base_1 = require("./base");
var ScheduleCtrl = /** @class */ (function (_super) {
    tslib_1.__extends(ScheduleCtrl, _super);
    function ScheduleCtrl() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.model = schedule_1.default;
        return _this;
    }
    return ScheduleCtrl;
}(base_1.default));
exports.default = ScheduleCtrl;
//# sourceMappingURL=schedule.js.map