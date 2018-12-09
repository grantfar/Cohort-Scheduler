"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = require("tslib");
var cohort_1 = require("../models/cohort");
var base_1 = require("./base");
var CohortCtrl = /** @class */ (function (_super) {
    tslib_1.__extends(CohortCtrl, _super);
    function CohortCtrl() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.model = cohort_1.default;
        return _this;
    }
    return CohortCtrl;
}(base_1.default));
exports.default = CohortCtrl;
//# sourceMappingURL=cohort.js.map