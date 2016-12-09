package eden.martin.collingwood

interface IAttackReport

class MissReport : IAttackReport
open class HitReport : IAttackReport
class SunkTargetReport(target : IShip) : HitReport()