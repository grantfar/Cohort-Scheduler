"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = require("tslib");
var assignment_1 = require("../models/assignment");
var base_1 = require("./base");
var AssignmentCtrl = /** @class */ (function (_super) {
    tslib_1.__extends(AssignmentCtrl, _super);
    function AssignmentCtrl() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.model = assignment_1.default;
        return _this;
    }
    return AssignmentCtrl;
}(base_1.default));
exports.default = AssignmentCtrl;
//# sourceMappingURL=assignment.js.map