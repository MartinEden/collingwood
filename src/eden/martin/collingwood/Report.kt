package eden.martin.collingwood

interface IReport

class MissReport : IReport
open class HitReport : IReport
class SunkTargetReport(val target : IShip) : HitReport()
class VictoryReport : IReport