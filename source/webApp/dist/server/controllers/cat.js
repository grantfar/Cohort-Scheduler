"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = require("tslib");
var cat_1 = require("../models/cat");
var base_1 = require("./base");
var CatCtrl = /** @class */ (function (_super) {
    tslib_1.__extends(CatCtrl, _super);
    function CatCtrl() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.model = cat_1.default;
        return _this;
    }
    return CatCtrl;
}(base_1.default));
exports.default = CatCtrl;
//# sourceMappingURL=cat.js.map