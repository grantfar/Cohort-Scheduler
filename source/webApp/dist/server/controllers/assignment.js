"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = require("tslib");
var assignment_1 = require("../models/assignment");
var base_1 = require("./base");
/*
MIT License

Copyright (c) 2018 Alex Markules, Jacob Kampf, Grant Farnsworth

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
var AssignmentCtrl = /** @class */ (function (_super) {
    tslib_1.__extends(AssignmentCtrl, _super);
    function AssignmentCtrl() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.model = assignment_1.default;
        _this.getByName = function (req, res) { return tslib_1.__awaiter(_this, void 0, void 0, function () {
            var docs, err_1;
            return tslib_1.__generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        _a.trys.push([0, 2, , 3]);
                        return [4 /*yield*/, this.model.find({ schedule: req.name })];
                    case 1:
                        docs = _a.sent();
                        res.status(200).json(docs);
                        return [3 /*break*/, 3];
                    case 2:
                        err_1 = _a.sent();
                        return [2 /*return*/, res.status(400).json({ error: err_1.message })];
                    case 3: return [2 /*return*/];
                }
            });
        }); };
        return _this;
    }
    return AssignmentCtrl;
}(base_1.default));
exports.default = AssignmentCtrl;
//# sourceMappingURL=assignment.js.map